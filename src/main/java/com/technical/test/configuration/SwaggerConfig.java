package com.technical.test.configuration;


import com.technical.test.shared.dto.ResponseApi;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer Token";
        return new OpenAPI()
                .info(new Info()
                        .title("Backend  API")
                        .version("1.0.0")
                        .contact(getContact())
                        .description("Backend For Technical Test")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))

                .servers(List.of(new Server().url("http://localhost:8080")))

                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
    @Bean
    public OperationCustomizer customize(OpenAPIService openAPIService){
        return (operation,method) ->{
            ApiResponses responses = operation.getResponses();
            if (method.getMethod().getReturnType().equals(ResponseApi.class)){
                Type type = ((ParameterizedType)((ParameterizedType)method.getMethod().getGenericReturnType()).getActualTypeArguments()[0]).getActualTypeArguments()[0];
                ResolvedSchema resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(type));
                Map<String, Schema> schemas = openAPIService.getCachedOpenAPI(Locale.US).getComponents().getSchemas();
                schemas.put(resolvedSchema.schema.getName(),resolvedSchema.schema);
                Schema schema = new ObjectSchema()
                        .type("object")
                        .addProperty("data",schemas.get(resolvedSchema.schema.getName()))
                        .name("ResponseBody"+resolvedSchema.schema.getName()+">");
                schemas.put("ResponseBody<"+resolvedSchema.schema.getName()+">",schema);
                responses.addApiResponse("Success",new ApiResponse()
                        .content(new Content().addMediaType("application/json",new MediaType()
                                .schema(new ObjectSchema().$ref(schema.getName())))));
            }

            return operation;
        };
    }

    private Contact getContact(){
        Contact  contact =new Contact();
        contact.setName("Contacto");
        contact.setEmail("jpduranhe@gmail.com");
        return contact;
    }



}

insert into users (created,email,last_login,modified,name,password,id,is_active) values (now(),'admin@mail.com',now(),now(),'Administrador Sistema','$2a$10$6OqaV/88V8dJucv90TcFRO.SOum7BkwQetHULfy57YTLqaMjbBCya','020a5b3b-f66e-46dd-85ff-c05f7bef964f',true);
insert into phones (city_code,country_code,number,user_id,id) values ('123','456','7890','020a5b3b-f66e-46dd-85ff-c05f7bef964f','1e0abbea-93e5-4627-ae73-953f499e6bc7');

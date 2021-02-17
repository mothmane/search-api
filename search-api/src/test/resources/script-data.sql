insert into example_entity(id,name,code,yes,same,creation_date) values(1,'nom01','CODE01', TRUE,'SAME','2001-01-22');
insert into example_entity(id,name,code,yes,same,creation_date) values(2,'nom02','CODE02', FALSE,'SAME','2002-01-22');
insert into example_entity(id,name,code,yes,same,creation_date) values(3,'nom03','CODE03', TRUE,'SAME','2003-01-22');
insert into example_entity(id,name,code,yes,same,creation_date) values(4,'nom04','CODE04', FALSE,'SAME','2004-01-22');

insert into relation_entity(id,name) values(1,'r-name01');
insert into relation_entity(id,name) values(2,'r-name02');
insert into relation_entity(id,name) values(3,'r-name03');
insert into relation_entity(id,name) values(4,'r-name04');
insert into relation_entity(id,name) values(5,'r-name05');


insert into example_entity_relations(example_entity_id,relations_id) values(1,1);
insert into example_entity_relations(example_entity_id,relations_id) values(1,2);
insert into example_entity_relations(example_entity_id,relations_id) values(1,3);
insert into example_entity_relations(example_entity_id,relations_id) values(2,4);
insert into example_entity_relations(example_entity_id,relations_id) values(2,5);


insert into example_entity_other_ids (example_entity_id,other_ids) values(1,'id01');


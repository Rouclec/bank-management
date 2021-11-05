create table Account (accountNumber varchar(255) not null, accountBalance bigint, expiration datetime(6), isActive bit not null, isSuspended bit not null, userName varchar(255), product_id bigint, primary key (accountNumber)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table Product (id bigint not null, description varchar(255), maximumAmount bigint, productName varchar(255), primary key (id)) engine=InnoDB
create table Savings (transactionId bigint not null, accountNumber varchar(255), amount bigint, date datetime(6), status varchar(255), primary key (transactionId)) engine=InnoDB
create table Shortee (id bigint not null, fullName varchar(255), idCardPhoto MEDIUMBLOB, primary key (id)) engine=InnoDB
create table Transactions (transactionId bigint not null, amount bigint, date datetime(6), receiverAccountNumber varchar(255), senderAccountNumber varchar(255), status varchar(255), transactionType varchar(255), primary key (transactionId)) engine=InnoDB
create table Transfer (transactionId bigint not null, amount bigint, date datetime(6), receiverAccountNumber varchar(255), senderAccountNumber varchar(255), status varchar(255), primary key (transactionId)) engine=InnoDB
create table User (userName varchar(255) not null, dateCreated datetime(6), email varchar(255), password varchar(255), phoneNumber varchar(255), role varchar(255), primary key (userName)) engine=InnoDB
create table User_accountList (User_userName varchar(255) not null, accountList_accountNumber varchar(255) not null) engine=InnoDB
create table User_shorteeList (User_userName varchar(255) not null, shorteeList_id bigint not null) engine=InnoDB
create table Withdrawal (transactionId bigint not null, accountNumber varchar(255), amount bigint, date datetime(6), status varchar(255), primary key (transactionId)) engine=InnoDB
alter table Account add constraint FK4a1qu9rncg8olsbykxgos9s64 foreign key (product_id) references Product (id)
alter table User_accountList add constraint FKfv66s0cxyvm6gnqfb1m6cy802 foreign key (accountList_accountNumber) references Account (accountNumber)
alter table User_accountList add constraint FKaq5etj85njcf2btn19cm7krri foreign key (User_userName) references User (userName)
alter table User_shorteeList add constraint FKrovm39pticw23dl11mmk81lo4 foreign key (shorteeList_id) references Shortee (id)
alter table User_shorteeList add constraint FKg7pbuln2lskjx7p8tbrr1wh19 foreign key (User_userName) references User (userName)

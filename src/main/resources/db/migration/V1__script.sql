CREATE TABLE  Withdrawal (
  transactionId bigint(20) NOT NULL,
  accountNumber varchar(255) DEFAULT NULL,
  amount bigint DEFAULT NULL,
  date datetime(6) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User` (
  `userName` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `lastModifiedBy` varchar(255) DEFAULT NULL,
  `lastModifiedOn` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Transfer` (
  `transactionId` bigint NOT NULL,
  `amount` bigint DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `receiverAccountNumber` varchar(255) DEFAULT NULL,
  `senderAccountNumber` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Transactions` (
  `transactionId` bigint NOT NULL,
  `amount` bigint DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `lastModifiedBy` varchar(255) DEFAULT NULL,
  `lastModifiedOn` datetime(6) DEFAULT NULL,
  `receiverAccountNumber` varchar(255) DEFAULT NULL,
  `senderAccountNumber` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `transactionType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Shortee` (
  `id` bigint NOT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `idCardPhoto` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Savings` (
  `transactionId` bigint NOT NULL,
  `accountNumber` varchar(255) DEFAULT NULL,
  `amount` bigint DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Product` (
  `id` bigint NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `maximumAmount` bigint DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Institution` (
  `id` bigint NOT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO hibernate_sequence(next_val)
VALUES(0);

CREATE TABLE `Account` (
  `accountNumber` varchar(255) NOT NULL,
  `accountBalance` bigint DEFAULT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `expiration` datetime(6) DEFAULT NULL,
  `isActive` bit(1) NOT NULL,
  `isSuspended` bit(1) NOT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`accountNumber`),
  KEY `FK4a1qu9rncg8olsbykxgos9s64` (`product_id`),
  CONSTRAINT `FK4a1qu9rncg8olsbykxgos9s64` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User_Shortee` (
  `User_userName` varchar(255) NOT NULL,
  `shorteeList_id` bigint NOT NULL,
  KEY `FKrovm39pticw23dl11mmk81lo4` (`shorteeList_id`),
  KEY `FKg7pbuln2lskjx7p8tbrr1wh19` (`user_userName`),
  CONSTRAINT `FKg7pbuln2lskjx7p8tbrr1wh19` FOREIGN KEY (`User_userName`) REFERENCES `User` (`userName`),
  CONSTRAINT `FKrovm39pticw23dl11mmk81lo4` FOREIGN KEY (`shorteeList_id`) REFERENCES `Shortee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User_Account` (
  `User_userName` varchar(255) NOT NULL,
  `accountList_accountNumber` varchar(255) NOT NULL,
  KEY `FKfv66s0cxyvm6gnqfb1m6cy802` (`accountList_accountNumber`),
  KEY `FKaq5etj85njcf2btn19cm7krri` (`User_userName`),
  CONSTRAINT `FKaq5etj85njcf2btn19cm7krri` FOREIGN KEY (`user_userName`) REFERENCES `User` (`userName`),
  CONSTRAINT `FKfv66s0cxyvm6gnqfb1m6cy802` FOREIGN KEY (`accountList_accountNumber`) REFERENCES `Account` (`accountNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
Insert into Product(id,createdBy,description,maximumAmount,productName)
values(0,"superadmin","current account", 1000000, "CURRENT ACCOUNT");
Insert into Product(id,createdBy,description,maximumAmount,productName)
values(1,"superadmin","savings account", 10000000, "SAVINGS ACCOUNT");
Insert into User (userName, createdBy, email, lastModifiedBy,password, phoneNumber, role)
values("superadmin","system","superadmin123@gmail.com","system","$2a$10$kMf43/Ob1H3MXdIUNqZ34ul/YJT3qMVVYO/laQLWquzy/g62EVle2","+237653368683","ROLE_SUPER_ADMIN");

Insert into User(userName, createdBy, email, lastModifiedBy,password, phoneNumber, role)
values("rouclec123","system","senatorasonganyi97@gmail.com","system","$2a$10$Uj2xz3K55VEsW0Vu831WYun2WJdLvJX7o24tHta7zqyA4gAOAlgBm","+237650184172","ROLE_USER");
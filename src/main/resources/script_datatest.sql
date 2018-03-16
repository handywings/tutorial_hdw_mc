

-- personnel #DATA TEST
INSERT INTO `personnel` (`id`, `createDate`, `createdBy`, `firstName`, `isDeleted`, `lastName`, `personnelCode`, `prefix`, `sex`, `updatedBy`, `updatedDate`, `companyId`, `permissionGroupId`, `positionId`, `technicianGroupId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ธนารัตน์', b'0', 'สุวรรณนันท์', 'EMP000001-2560', 'นาย', 'male', NULL, NULL, '1', '1', '6', NULL),
(NULL, CURRENT_TIMESTAMP, 'System', 'ทดลอง1', b'0', 'ทดลอง1', 'EMP000002-2560', 'นาย', 'male', NULL, NULL, '1', '1', '6', NULL),
(NULL, CURRENT_TIMESTAMP, 'System', 'ทดลอง2', b'0', 'ทดลอง2', 'EMP000003-2560', 'นาย', 'male', NULL, NULL, '1', '1', '6', NULL),
(NULL, CURRENT_TIMESTAMP, 'System', 'ทดลอง3', b'0', 'ทดลอง3', 'EMP000004-2560', 'นาย', 'male', NULL, NULL, '1', '1', '6', NULL);

INSERT INTO `contact` 
(`id`, `contactType`, `createDate`, `createdBy`, `email`, `facebook`, `fax`, `ig`, `line`, `mobile`, `twitter`, `updatedBy`, `updatedDate`, `website`, `companyId`, `personnelId`,`isDeleted`) 
VALUES 
(NULL, NULL, CURRENT_TIMESTAMP, 'System', 'handywings1@gmail.com', NULL, '05483890', NULL, NULL, '0801029893', NULL, NULL, NULL, NULL, NULL, 1, b'0'),
(NULL, NULL, CURRENT_TIMESTAMP, 'System', 'handywings2@gmail.com', NULL, '05483891', NULL, NULL, '0801029894', NULL, NULL, NULL, NULL, NULL, 2, b'0'),
(NULL, NULL, CURRENT_TIMESTAMP, 'System', 'handywings3@gmail.com', NULL, '05483892', NULL, NULL, '0801029895', NULL, NULL, NULL, NULL, NULL, 3, b'0'),
(NULL, NULL, CURRENT_TIMESTAMP, 'System', 'handywings4@gmail.com', NULL, '05483893', NULL, NULL, '0801029896', NULL, NULL, NULL, NULL, NULL, 4, b'0');

-- technician_group #DATA TEST
INSERT INTO `technician_group` (`id`, `createDate`, `createdBy`, `isDeleted`, `technicianGroupName`, `updatedBy`, `updatedDate`, `personnelId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'งานเดินสาย', NULL, NULL, '1'),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'งานติดตั้ง', NULL, NULL, '2');

-- update personnel to technicianGroupId
UPDATE `personnel` SET `technicianGroupId` = '1' WHERE `personnel`.`id` = 1;
UPDATE `personnel` SET `technicianGroupId` = '2' WHERE `personnel`.`id` = 2;
UPDATE `personnel` SET `technicianGroupId` = '1' WHERE `personnel`.`id` = 3;
UPDATE `personnel` SET `technicianGroupId` = '2' WHERE `personnel`.`id` = 4;

-- internet_product #DATA TEST
INSERT INTO `internet_product` (`id`, `createDate`, `createdBy`, `isDeleted`, `productCode`, `productName`, `updatedBy`, `updatedDate`, `productCategoryId`, `stockId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'I-00001', 'Internet 50 Mbsp รายเดือน 590 บาท ฟรีค่าติดตั้ง + ค่าอุปกรณ์', NULL, NULL, '2', '1'),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'I-00002', 'Internet 60 Mbsp รายเดือน 690 บาท ฟรีค่าติดตั้ง + ค่าอุปกรณ์', NULL, NULL, '2', '1'),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'I-00003', 'Internet 70 Mbsp รายเดือน 790 บาท ฟรีค่าติดตั้ง + ค่าอุปกรณ์', NULL, NULL, '2', '1');

-- internet_product_item #DATA TEST
INSERT INTO `internet_product_item` (`id`, `createDate`, `createdBy`, `isDeleted`, `status`, `password`, `reference`, `updatedBy`, `updatedDate`, `userName`, `internetProductId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '1234', 'REF-0001', NULL, NULL, 'test1', '1'),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '1235', 'REF-0001', NULL, NULL, 'test2', '2'),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '1236', 'REF-0001', NULL, NULL, 'test3', '3');





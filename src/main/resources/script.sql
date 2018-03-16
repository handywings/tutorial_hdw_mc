
INSERT INTO `permission_group` (`id`, `createDate`, `createdBy`, `permissionGroupDesc`, `permissionGroupName`, `permissionType`, `status`, `updatedBy`, `updatedDate`,`isDeleted`) VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'permissionGroupDefault', 'Default', 'Default', b'1', NULL, NULL, b'0');

SELECT LAST_INSERT_ID() INTO @permission_group;


-- ภาพรวมระบบ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ภาพรวมระบบ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M1');

-- ตั้งค่าข้อมูลพื้นฐาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ตั้งค่าข้อมูลพื้นฐาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M2');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'สิทธิ์การใช้งานระบบ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M3'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลบริษัท', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M4'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลตำแหน่งงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M5'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลเขตชุมชน/โครงการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M6'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการหน่วยนับ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M7'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ประเภทสินค้าและบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M8'),
(NULL, CURRENT_TIMESTAMP, 'System', 'สำรองข้อมูล', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M9');

-- ข้อมูลพนักงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลพนักงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M10');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระเบียนพนักงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M11'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลกลุ่มช่างเทคนิค', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M12'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลพนักงานเก็บเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M13');


-- รายการ Package บริการ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'Package บริการ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M14');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการ Package บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M46');


-- ระบบคลังสินค้า / บริการ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบคลังสินค้า / บริการ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M15');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลคลังสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M16'),
(NULL, CURRENT_TIMESTAMP, 'System', 'หมวดหมู่วัสดุอุปกรณ์', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M17'),
(NULL, CURRENT_TIMESTAMP, 'System', 'นำเข้าสินค้า / เพิ่มบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M18'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการสินค้าและบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M19'),
(NULL, CURRENT_TIMESTAMP, 'System', 'เบิกสินค้าและอุปกรณ์', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M20'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบเบิกสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M21');


-- ระบบลูกค้าสัมพันธ์

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบลูกค้าสัมพันธ์', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M22');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'สมัครใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M23'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ทะเบียนลูกค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M24'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบสมัครใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M25'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ใบสมัครแยกตามเขตพื้นที่', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M26'),
(NULL, CURRENT_TIMESTAMP, 'System', 'แจ้งยกเลิก/เปลี่ยนการใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M27');


-- ระบบจัดการข้อมูลงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบจัดการข้อมูลงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M28');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'เพิ่มใบงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M29'),
(NULL, CURRENT_TIMESTAMP, 'System', 'มอบหมายงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M30'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบงานทั้งหมด', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M31'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบสมัครรอ CA', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M32');


-- ระบบการเงิน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบการเงิน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M33');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบแจ้งหนี้ / ชำระเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M34'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ใบเสร็จรับเงิน / ใบกำกับภาษี', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M35'),
(NULL, CURRENT_TIMESTAMP, 'System', 'สร้างใบแจ้งหนี้ตามรอบบิล', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M36'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลลูกค้าค้างชำระ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M37'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ตรวจสอบใบเสร็จรับเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M38'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการคืนเงินมัดจำ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M39'),
(NULL, CURRENT_TIMESTAMP, 'System', 'พิมพ์ใบแจ้งหนี้', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M40');

-- รายงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M41');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานทะเบียนลูกค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M42'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานการเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M43'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานคลังสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M44'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานใบงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M45');


-- permission group for admin init 
INSERT INTO `permission_group` (`id`, `createDate`, `createdBy`, `permissionGroupDesc`, `permissionGroupName`, `permissionType`, `status`, `updatedBy`, `updatedDate`,`isDeleted`) VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ใช้สำหรับผู้ที่มีสิทธิ์สูงสุดในระบบ', 'กลุ่มผู้ดูแลระบบ', 'init', b'1', NULL, NULL, b'0');

SELECT LAST_INSERT_ID() INTO @permission_group;


-- ภาพรวมระบบ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ภาพรวมระบบ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M1');

-- ตั้งค่าข้อมูลพื้นฐาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ตั้งค่าข้อมูลพื้นฐาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M2');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'สิทธิ์การใช้งานระบบ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M3'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลบริษัท', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M4'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลตำแหน่งงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M5'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลเขตชุมชน/โครงการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M6'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการหน่วยนับ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M7'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ประเภทสินค้าและบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M8'),
(NULL, CURRENT_TIMESTAMP, 'System', 'สำรองข้อมูล', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M9');

-- ข้อมูลพนักงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลพนักงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M10');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระเบียนพนักงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M11'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลกลุ่มช่างเทคนิค', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M12'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลพนักงานเก็บเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M13');


-- รายการ Package บริการ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'Package บริการ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M14');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการ Package บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M46');

-- ระบบคลังสินค้า / บริการ

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบคลังสินค้า / บริการ', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M15');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลคลังสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M16'),
(NULL, CURRENT_TIMESTAMP, 'System', 'หมวดหมู่วัสดุอุปกรณ์', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M17'),
(NULL, CURRENT_TIMESTAMP, 'System', 'นำเข้าสินค้า / เพิ่มบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M18'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการสินค้าและบริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M19'),
(NULL, CURRENT_TIMESTAMP, 'System', 'เบิกสินค้าและอุปกรณ์', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M20'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบเบิกสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M21');


-- ระบบลูกค้าสัมพันธ์

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบลูกค้าสัมพันธ์', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M22');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'สมัครใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M23'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ทะเบียนลูกค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M24'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบสมัครใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M25'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ใบสมัครแยกตามเขตพื้นที่', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M26'),
(NULL, CURRENT_TIMESTAMP, 'System', 'แจ้งยกเลิกใช้บริการ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M27');


-- ระบบจัดการข้อมูลงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบจัดการข้อมูลงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M28');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'เพิ่มใบงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M29'),
(NULL, CURRENT_TIMESTAMP, 'System', 'มอบหมายงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M30'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบงานทั้งหมด', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M31'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบสมัครรอ CA', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M32');


-- ระบบการเงิน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ระบบการเงิน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M33');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการใบแจ้งหนี้ / ชำระเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M34'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ใบเสร็จรับเงิน / ใบกำกับภาษี', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M35'),
(NULL, CURRENT_TIMESTAMP, 'System', 'สร้างใบแจ้งหนี้ตามรอบบิล', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M36'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ข้อมูลลูกค้าค้างชำระ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M37'),
(NULL, CURRENT_TIMESTAMP, 'System', 'ตรวจสอบใบเสร็จรับเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M38'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายการคืนเงินมัดจำ', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M39'),
(NULL, CURRENT_TIMESTAMP, 'System', 'พิมพ์ใบแจ้งหนี้', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M40');

-- รายงาน

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงาน', b'0', b'1', b'0', b'0', b'0', 0, NULL, NULL, @permission_group, b'0','M41');

SELECT LAST_INSERT_ID() INTO @function;

INSERT INTO `function` (`id`, `createDate`, `createdBy`, `functionName`, `isAdd`, `isCheck`, `isDelete`, `isEdit`, `isView`, `parent`, `updatedBy`, `updatedDate`, `permissionGroupId`,`isDeleted`,`keyName`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานทะเบียนลูกค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M42'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานการเงิน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M43'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานคลังสินค้า', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M44'),
(NULL, CURRENT_TIMESTAMP, 'System', 'รายงานใบงาน', b'1', b'1', b'1', b'1', b'1', @function, NULL, NULL, @permission_group, b'0','M45');

-- end permission group template and init



-- company

INSERT INTO `company` 
(`id`, `companyName`, `createDate`, `createdBy`, `invCredit`, `parent`, `taxId`, `updatedBy`, `updatedDate`, `vat`,`isDeleted`) 
VALUES (NULL, 'บริษัท เคเบิล จำกัด', CURRENT_TIMESTAMP, 'System', '7', '0', '9803098920299', NULL, NULL, '7', b'0');


SELECT LAST_INSERT_ID() INTO @company;

-- address

INSERT INTO `address` 
(`id`, `addressType`, `alley`, `building`, `country`, `createDate`, `createdBy`, `detail`, `district`, `floor`, `latitude`, `longitude`, `no`, `postcode`, `province`, `road`, `room`, `section`, `subdistrict`, `updatedBy`, `updatedDate`, `village`, `companyId`,`isDeleted`) 
VALUES 
(NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'System', '88/5 หมู่ที่ 8 หมู่บ้านโกลเด้นทาวน์ ปิ่นเกล้า-จรัญสนิทวงศ์ ต.บางกรวย อ.บางกรวย จ.นนทบุรี 11130', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, @company, b'0');

-- contact

INSERT INTO `contact` 
(`id`, `contactType`, `createDate`, `createdBy`, `email`, `facebook`, `fax`, `ig`, `line`, `mobile`, `twitter`, `updatedBy`, `updatedDate`, `website`, `companyId`, `personnelId`,`isDeleted`) 
VALUES (NULL, NULL, CURRENT_TIMESTAMP, 'System', 'handywings@gmail.com', NULL, '05483890', NULL, NULL, '0801029893', NULL, NULL, NULL, NULL, @company, NULL, b'0');

-- position

INSERT INTO `position` (`id`, `createDate`, `createdBy`, `desctiption`, `isDeleted`, `positionName`, `updatedBy`, `updatedDate`, `companyId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', 'ตำแหน่งที่ดูแลเกี่ยวกับการขาย และรับสมัครลูกค้า', b'0', 'ฝ่ายขาย/รับสมัคร', NULL, NULL, @company),
(NULL, CURRENT_TIMESTAMP, 'System', 'ตัดสต็อกและนำอุปกรณ์ไปติดตั้งให้ลูกค้า', b'0', 'ฝ่าย Stock และติดตั้งอุปกรณ์', NULL, NULL, @company),
(NULL, CURRENT_TIMESTAMP, 'System', 'ดูแลข้อมูลทางด้านการเงิน', b'0', 'ฝ่ายการเงิน', NULL, NULL, @company),
(NULL, CURRENT_TIMESTAMP, 'System', 'Administrator', b'0', 'ฝ่าย Admin', NULL, NULL, @company),
(NULL, CURRENT_TIMESTAMP, 'System', 'ตอนรับลูกค้า เก็บเงิน ออกใบเสร็จ', b'0', 'ฝ่ายธุรการ', NULL, NULL, @company),
(NULL, CURRENT_TIMESTAMP, 'System', 'ช่าง', b'0', 'ฝ่ายช่าง', NULL, NULL, @company);

SELECT LAST_INSERT_ID() INTO @position;


-- equipment_product_category

INSERT INTO `equipment_product_category`
 (`createDate`, `createdBy`, `description`, `isDeleted`, `equipmentProductCategoryCode`, `equipmentProductCategoryName`, `updatedBy`, `updatedDate`) 
 VALUES 
 (CURRENT_TIMESTAMP, 'System',NULL,b'0','00001','วัสดุอุปกรณ์สินค้า',NULL,NULL),
 (CURRENT_TIMESTAMP, 'System',NULL,b'0','00002','บริการ Internet User',NULL,NULL),
 (CURRENT_TIMESTAMP, 'System',NULL,b'0','00003','ค่าบริการ',NULL,NULL);

-- service_package_type
INSERT INTO `service_package_type` 
(`createDate`, `createdBy`, `description`, `isDeleted`, `packageTypeCode`, `packageTypeName`, `updatedBy`, `updatedDate`) 
VALUES 
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ เคเบิล', b'0', '00001', 'เคเบิล', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ กล้องวงจรปิด', b'0', '00002', 'กล้องวงจรปิด', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ Internet', b'0', '00003', 'Internet', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ เคเบิล + กล้องวงจรปิด + Internet', b'0', '00004', 'เคเบิล + กล้องวงจรปิด + Internet', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ เคเบิล + Internet', b'0', '00005', 'เคเบิล + Internet', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ เคเบิล + กล้องวงจรปิด', b'0', '00006', 'เคเบิล + กล้องวงจรปิด', NULL, NULL),
(CURRENT_TIMESTAMP, 'System', 'รายละเอียดบริการ Internet + กล้องวงจรปิด', b'0', '00007', 'Internet + กล้องวงจรปิด', NULL, NULL);

 -- unit
 INSERT INTO `unit` (`id`, `createDate`, `createdBy`, `isDeleted`, `unitName`, `updatedBy`, `updatedDate`) 
 VALUES 
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'หน่วย', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'จุด', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'อัน', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'ชิ้น', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'เมตร', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'ใบ', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'ตัว', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'งาน', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'คู่', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'กิโลกรัม', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'มิลลิกรัม', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'แท่ง', NULL, NULL),
 (NULL, CURRENT_TIMESTAMP, 'System', b'0', 'user', NULL, NULL);	
  
-- stock1
INSERT INTO `stock` (`id`, `createDate`, `createdBy`, `isDeleted`, `stockCode`, `stockDetail`, `stockName`, `updatedBy`, `updatedDate`, `companyId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'STOCK-0000001', NULL, 'สต๊อก กรุงเทพฯ', NULL, NULL, @company);

SELECT LAST_INSERT_ID() INTO @stock;

INSERT INTO `address` 
(`createDate`, `createdBy`, `detail`, `stockId`,`isDeleted`) 
VALUES 
(CURRENT_TIMESTAMP, 'System', '604/3 ถนน เพชรบุรี แขวง ถนนเพชรบุรี เขต ราชเทวี กรุงเทพมหานคร 10400', @stock, b'0');

-- service_product #DATA Master
INSERT INTO `service_product` (`id`, `createDate`, `createdBy`, `isDeleted`, `price`, `productCode`, `serviceChargeName`, `updatedBy`, `updatedDate`, `productCategoryId`, `unitId`,`stockId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00001', 'ค่าแรงติดตั้ง', NULL, NULL, '3', (select id from unit where id = 1), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00002', 'ค่าติดตั้งจุด Digital', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00003', 'ค่าติดตั้งจุด Analog', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00004', 'ค่าบริการเสริมจุด Digital', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00005', 'ค่าบริการเสริมจุด Analog', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00006', 'ค่าบริการย้ายจุด', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00007', 'ค่าบริการเชื่อมสาย', NULL, NULL, '3', (select id from unit where id = 2), @stock),
(NULL, CURRENT_TIMESTAMP, 'System', b'0', '0', '00008', 'ค่าบริการย้ายสาย', NULL, NULL, '3', (select id from unit where id = 2), @stock);


-- stock2
INSERT INTO `stock` (`id`, `createDate`, `createdBy`, `isDeleted`, `stockCode`, `stockDetail`, `stockName`, `updatedBy`, `updatedDate`, `companyId`) 
VALUES 
(NULL, CURRENT_TIMESTAMP, 'System', b'0', 'STOCK-0000002', NULL, 'สต๊อก นนทบุรี', NULL, NULL, @company);

SELECT LAST_INSERT_ID() INTO @stock;

INSERT INTO `address` 
(`createDate`, `createdBy`, `detail`, `stockId`,`isDeleted`) 
VALUES 
(CURRENT_TIMESTAMP, 'System', 'เลขที่ 88/5 หมู่บ้านโกลเด้นทาว์น จรัญ-ปิ่นเกล้า หมู่ 8 ตำบลบางกรวย อำเภอบางกรวย จังหวัดนนทบุรี 11130', @stock, b'0');

-- career
INSERT INTO `career` 
(`careerName`, `careerCode`) 
VALUES 
('ค้าขาย','0001'),
('รับจ้าง','0002'),
('ตำรวจ (แฟลต)','0003'),
('ตำรวจ (บ้าน)','0004'),
('บริษัท /ห้างร้าน','0005'),
('พระภิกษุ /สามเณร','0006'),
('พ่อบ้าน/แม่บ้าน','0007'),
('ธุรกิจส่วนตัว','0008'),
('สรรพากร','0009'),
('รับราชการ','0010'),
('รัฐวิสาหกิจ','0011'),
('แพทย์/พยาบาล','0012'),
('นักศึกษา','0013'),
('ทนายความ','0014'),
('ทหาร','0015'),
('ธุรกิจส่วนตัว (ตร.)','0016'),
('ประมง','0017'),
('พระ/สามเณร','0018'),
('พ่อค้า,นักธุรกิจ','0019'),
('แม่บ้าน','0020'),
('แม่บ้าน/พ่อบ้าน','0021'),
('รับจ้าง(ธนาคาร)','0022'),
('รับราชการ (ครู)','0023'),
('รับราชการ (ผู้คุม)','0024'),
('รับราชการ (อบต.)','0025'),
('รับราชการ (อัยการ)','0026'),
('รับราชการ(สรรพสามิตร','0027'),
('วิศวกร','0028'),
('อื่นๆ','0029');

-- customer_feature
INSERT INTO `customer_feature` 
(`customerFeatureName`, `customerFeatureCode`) 
VALUES 
('สมาชิกบ้าน (ที่พักอาศัยส่วนตัว )','0001'),
('สมาชิกโครงการ (ราคาเหมาจ่าย)','0002'),
('สมาชิก VIP (ไม่เสียค่าบริการ)','0003');

-- service_application_type
INSERT INTO `service_application_type` 
(`serviceApplicationTypeName`, `serviceApplicationTypeCode`) 
VALUES 
('สมาชิกรายเดือน','0001'),
('สมาชิกรายครึ่งปี','0002'),
('สมาชิกรายปี','0003'),
('สมาชิกถูกตัดสาย','0004');

-- bank 
INSERT INTO `bank` (`id`, `bankCode`,`bankNameEn`,`bankNameTh`,`bankShortName`)
VALUES 
(NULL, '002', NULL, 'ธนาคารกรุงเทพ','BBL'),
(NULL, '004', NULL, 'ธนาคารกสิกรไทย','KBANK'),
(NULL, '006', NULL, 'ธนาคารกรุงไทย','KTB'),
(NULL, '008', NULL, 'ธนาคารเจพีมอร์แกน เชส สาขากรุงเทพฯ','JPMC'),
(NULL, '011', NULL, 'ธนาคารทหารไทย','TMB'),
(NULL, '014', NULL, 'ธนาคารไทยพาณิชย์','SCB'),
(NULL, '017', NULL, 'ธนาคารซิตี้แบงก์','CITI'),
(NULL, '018', NULL, 'ธนาคารชูมิโตโม มิตซุย แบงกิ้ง คอร์ปอเรชั่น','SMBC'),
(NULL, '020', NULL, 'ธนาคารสแตนดาร์ดชาร์เตอร์ด (ไทย)','SCBT'),
(NULL, '022', NULL, 'ธนาคาร ซีไอเอ็มบี ไทย','CIMBT'),
(NULL, '024', NULL, 'ธนาคารยูโอบี','UOBT'),
(NULL, '025', NULL, 'ธนาคารกรุงศรีอยุธยา','BAY'),
(NULL, '026', NULL, 'ธนาคารเมกะ สากลพาณิชย์','MEGA ICBC'),
(NULL, '027', NULL, 'ธนาคารแห่งอเมริกา เนชั่นแนล แอสโซซิเอชั่น','AMERICA'),
(NULL, '030', NULL, 'ธนาคารออมสิน','GSB'),
(NULL, '031', NULL, 'ธนาคารฮ่องกงและเซี่ยงไฮ้','HSBC'),
(NULL, '032', NULL, 'ธนาคารดอยซ์แบงก์','DEUTSCHE'),
(NULL, '033', NULL, 'ธนาคารอาคารสงเคราะห์','GHB'),
(NULL, '034', NULL, 'ธนาคารเพื่อการเกษตรและสหกรณ์การเกษตร','BAAC'),
(NULL, '039', NULL, 'ธนาคารมิซูโฮ จำกัด สาขากรุงเทพฯ','MHCB'),
(NULL, '045', NULL, 'ธนาคาร บีเอ็นพี พารีบาส์ สาขากรุงเทพฯ','BNPP'),
(NULL, '052', NULL, 'ธนาคารแห่งประเทศจีน สาขากรุงเทพฯ','BOC'),
(NULL, '065', NULL, 'ธนาคารธนชาต','TBANK'),
(NULL, '066', NULL, 'ธนาคารอิสลามแห่งประเทศไทย','ISBT'),
(NULL, '067', NULL, 'ธนาคารทิสโก้','TISCO'),
(NULL, '069', NULL, 'ธนาคารเกียรตินาคิน','KKB'),
(NULL, '070', NULL, 'ธนาคารไอซีบีซี (ไทย)','ICBC'),
(NULL, '071', NULL, 'ธนาคารไทยเครดิต เพื่อรายย่อย','TCRB'),
(NULL, '079', NULL, 'ธนาคารเอเอ็นแซด (ไทย) จำกัด (มหาชน)','ANZ');
 

create database CarRental

use CarRental

create table tblUser(
    email nvarchar(50) PRIMARY KEY,
	password varchar(50),
    phone varchar(20),
    address nvarchar(150),
    createDate date,
    status varchar(10) 
)

alter table tblUser
add fullname varchar(50)

create table tblCategory(
    categoryID varchar(20) PRIMARY KEY,
    categoryName nvarchar(50)
)

create table tblCar(
    carID varchar(20) PRIMARY KEY,
    carName nvarchar(20),
    image nvarchar(250),
    color nvarchar(50),
    year date,
    price real,
    quantity int,
    categoryId varchar(20) FOREIGN KEY REFERENCES tblCategory(categoryId)
)

CREATE TABLE tblOrderDetail(
    carID varchar(20) FOREIGN KEY REFERENCES tblCar(carID),
    orderID varchar(20) FOREIGN KEY REFERENCES tblOrder(orderID),
    quantity int,
    total real,
    PRIMARY KEY (carID, orderD)
)

alter table tblOrderDetail
add point int

create table tblOrder(
    orderID varchar(20) primary key,
    bookingDate date,
    status varchar(20),
    userID nvarchar(50) FOREIGN KEY REFERENCES tblUser(email),
    discountCode varchar(20) FOREIGN KEY REFERENCES tblDiscount(discountCode)
)

alter table tblOrder


create table tblDiscount(
    discountCode varchar(20) PRIMARY KEY,
    expiryDate date,
    discount int
)

create table tblFeedback(
    carID varchar(20) FOREIGN KEY REFERENCES tblCar(carID),
    orderID varchar(20) FOREIGN KEY REFERENCES tblOrder(orderID),
    content nvarchar(250),
	PRIMARY KEY(carID, orderID)
)

INSERT INTO tblUser(email, password, fullname, phone, address, createDate, status, role)
VALUES ('huy123', '123', 'huy', '090123456', 'Quan 1', '2021-12-12', 'new', 'MEMBER')

UPDATE tblUser
SET status = 'active'
WHERE email = 'nhoxroney9x1@gmail.com'

SELECT carID, carName, image, color, year, price, quantity, ca.categoryName
FROM tblCar c join tblCategory ca on c.categoryId = ca.categoryID
WHERE quantity > 0
GROUP BY carID, carName, image, color, year, price, quantity, ca.categoryName
ORDER BY year DESC
OFFSET 0 ROWS
FETCH NEXT 20 ROWS ONLY

SELECT count(carID) as totalCar 
FROM tblCar
WHERE quantity > 0

SELECT carID, carName, image, color, year, price, quantity, ca.categoryName
FROM tblCar c join tblCategory ca ON c.categoryID = ca.categoryID
WHERE quantity > 0
GROUP BY carID, carName, image, color, year, price, quantity, ca.categoryName
ORDER BY year DESC 
OFFSET 0 ROWS 
FETCH NEXT 20 ROWS ONLY 

select image 
from tblCar

UPDATE tblCar
SET image = 'https://imgur.com/Mi8rNPf'
WHERE image like '123'

SELECT c.carID, od.quantity
FROM tblCar c join tblOrderDetail od on c.carID = od.carID join tblOrder o on od.orderID = o.orderID
WHERE ((bookingDate BETWEEN '' AND '') OR (returnDate BETWEEN '' AND ''))

select carID
FROM tblCar
where quantity >= 1 and categoryId = '4'

SELECT carID, image, carName, color, year, price, quantity, ca.categoryName
FROM tblCar c JOIN tblCategory ca ON c.categoryId = ca.categoryID
WHERE carName = 'Porsche Panamera'

SELECT discountCode, expiryDate, discount
FROM tblDiscount
WHERE discountCode = 'sale10'

INSERT tblOrderDetail(orderID, carID, quantity, total) 
VALUES (1, 'com1', 2, 2000)

SELECT orderID, userID, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount
FROM tblOrder o join tblUser u on o.userID = u.email
WHERE o.userID = 'hoangvd'
GROUP BY o.orderID, userID, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount
ORDER BY bookingDate DESC

SELECT carName, price, od.quantity
FROM tblCar c join tblOrderDetail od on c.carID = od.carID
WHERE orderID = '100221-111847-084938'

UPDATE tblOrder
SET status = 'active'
WHERE orderID = '100221-045632-567160'

SELECT orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
FROM tblOrder 
GROUP BY orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
ORDER BY bookingDate DESC

SELECT o.orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
FROM tblOrder o, tblOrderDetail od,  tblCar c
WHERE o.orderID = od.orderID AND od.carID = c.carID 
	AND (bookingDate BETWEEN '2021-02-12' AND '2021-02-17') 

GROUP BY o.orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
ORDER BY bookingDate DESC
AND carName like '%Nissan%'

SELECT u.fullname, feedback, point
FROM tblCar c, tblOrderDetail od, tblOrder o, tblUser u
WHERE c.carID = od.carID and od.orderID = o.orderID and o.userID = u.email
AND c.carID = 'lux1'

UPDATE tblOrderDetail
SET feedback = 'excellent', point = '9'
FROM tblCar c join tblOrderDetail od on c.carID = od.carID join tblOrder o on od.orderID = o.orderID
WHERE od.carID = 'lux1' and od.orderID = '100221-035604-171442'

SELECT od.carID, carName, image, price, od.quantity
FROM tblCar c join tblOrderDetail od on c.carID = od.carID
WHERE orderID = '100221-045632-567160'

SELECT c.carID, sum(od.quantity) as quantity
FROM tblCar c, tblOrderDetail od, tblOrder o
WHERE c.carID = od.carID AND od.orderID = o.orderID	
AND ((bookingDate BETWEEN '2021-02-12' AND '2021-02-14') OR (returnDate BETWEEN '2021-02-12' AND '2021-02-14'))
GROUP BY c.carID 

SELECT TOP 5 carID
FROM tblCar
ORDER BY NEWID()
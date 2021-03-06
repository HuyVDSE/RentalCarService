USE [master]
GO
/****** Object:  Database [CarRental]    Script Date: 2/19/2021 9:57:30 AM ******/
CREATE DATABASE [CarRental]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CarRental', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\CarRental.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'CarRental_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\CarRental_log.ldf' , SIZE = 832KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [CarRental] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CarRental].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CarRental] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CarRental] SET ARITHABORT OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [CarRental] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CarRental] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CarRental] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CarRental] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CarRental] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CarRental] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CarRental] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CarRental] SET  ENABLE_BROKER 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CarRental] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CarRental] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CarRental] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CarRental] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CarRental] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CarRental] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CarRental] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [CarRental] SET  MULTI_USER 
GO
ALTER DATABASE [CarRental] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CarRental] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CarRental] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CarRental] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [CarRental] SET DELAYED_DURABILITY = DISABLED 
GO
USE [CarRental]
GO
/****** Object:  Table [dbo].[tblCar]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblCar](
	[carID] [varchar](20) NOT NULL,
	[carName] [nvarchar](20) NULL,
	[image] [nvarchar](250) NULL,
	[color] [nvarchar](50) NULL,
	[year] [date] NULL,
	[price] [real] NULL,
	[quantity] [int] NULL,
	[categoryId] [varchar](20) NULL,
	[description] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[carID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblCategory]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblCategory](
	[categoryID] [varchar](20) NOT NULL,
	[categoryName] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblDiscount]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblDiscount](
	[discountCode] [varchar](20) NOT NULL,
	[expiryDate] [date] NULL,
	[discount] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[discountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrder]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrder](
	[orderID] [varchar](20) NOT NULL,
	[bookingDate] [date] NULL,
	[status] [varchar](20) NULL,
	[userID] [nvarchar](50) NULL,
	[returnDate] [date] NULL,
	[totalAmount] [real] NULL,
	[totalAfterDiscount] [real] NULL,
PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrderDetail]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrderDetail](
	[carID] [varchar](20) NOT NULL,
	[orderID] [varchar](20) NOT NULL,
	[quantity] [int] NULL,
	[total] [real] NULL,
	[feedback] [nvarchar](500) NULL,
	[point] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[carID] ASC,
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUser]    Script Date: 2/19/2021 9:57:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUser](
	[email] [nvarchar](50) NOT NULL,
	[phone] [varchar](20) NULL,
	[address] [nvarchar](150) NULL,
	[createDate] [date] NULL,
	[status] [varchar](10) NULL,
	[role] [varchar](10) NULL,
	[password] [varchar](50) NULL,
	[fullname] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'com1', N'Mazda3', N'https://i.imgur.com/Mi8rNPf.jpg', N'Red', CAST(N'2018-04-20' AS Date), 350, 12, N'2', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'com2', N'Nissan Versa', N'https://i.imgur.com/sxJWfam.jpg', N'Red', CAST(N'2019-07-20' AS Date), 360, 10, N'2', N'4 seats, 2 bags, 4 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'com3', N'Kia Forte', N'https://i.imgur.com/OzIwdKU.png', N'Gray', CAST(N'2017-05-19' AS Date), 270, 3, N'2', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'com4', N'Volkswagen Jetta', N'https://i.imgur.com/YqmSH7l.png', N'Blue', CAST(N'2016-01-20' AS Date), 500, 2, N'2', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'com5', N'Ford Focus', N'https://i.imgur.com/OzIwdKU.png', N'Blue', CAST(N'2019-03-25' AS Date), 470, 4, N'2', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'eco1', N'Honda Insight', N'https://i.imgur.com/FJ8QMPD.jpg', N'Blue', CAST(N'2020-01-22' AS Date), 300, 10, N'1', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'eco2', N'Toyota Prius', N'https://i.imgur.com/5kYM1R7.png', N'White', CAST(N'2020-02-22' AS Date), 250, 5, N'1', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'eco3', N'Hyundai Ioniq', N'https://i.imgur.com/OfOv7Xa.jpg', N'Red', CAST(N'2020-01-19' AS Date), 200, 7, N'1', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'eco4', N'Toyota Corolla', N'https://i.imgur.com/Dx0Rkw9.jpg', N'Black', CAST(N'2020-07-15' AS Date), 270, 8, N'1', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'eco5', N'Honda Accord', N'https://i.imgur.com/DRr1gZW.jpg', N'Black', CAST(N'2020-05-15' AS Date), 300, 10, N'1', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'inter1', N'Nissan Sentra', N'https://i.imgur.com/gl0lQqy.jpg', N'Orange', CAST(N'2020-10-11' AS Date), 500, 3, N'3', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'inter2', N'Hyundai Elantra', N'https://i.imgur.com/bvqrKBM.png', N'White', CAST(N'2019-07-14' AS Date), 550, 6, N'3', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'inter3', N'Ford EcoSport', N'https://i.imgur.com/jiumYv2.png', N'Brown', CAST(N'2017-06-18' AS Date), 600, 3, N'3', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'inter4', N'Toyota Corolla', N'https://i.imgur.com/Dx0Rkw9.jpg', N'White', CAST(N'2019-08-20' AS Date), 660, 2, N'3', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'inter5', N'Chevrolet Cruze', N'https://i.imgur.com/8HROxZI.jpg', N'White', CAST(N'2018-11-20' AS Date), 580, 4, N'3', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'lux1', N'Mercedes Benz C63', N'https://i.imgur.com/a2meEOU.jpg', N'White', CAST(N'2020-09-09' AS Date), 700, 2, N'4', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'lux2', N'Audi S5', N'https://i.imgur.com/kNBX4GF.jpg', N'Blue', CAST(N'2020-10-20' AS Date), 780, 3, N'4', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'lux3', N'Mercedes-Benz C43', N'https://i.imgur.com/FemFWcn.jpg', N'Black', CAST(N'2021-01-20' AS Date), 690, 4, N'4', N'2 seats, 1 bags, 2 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'lux4', N'Porsche Panamera', N'https://i.imgur.com/jrS8KTF.jpg', N'White', CAST(N'2020-10-27' AS Date), 800, 1, N'4', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'lux5', N'Genesis G90', N'https://i.imgur.com/YiEy3mB.jpg', N'Black', CAST(N'2019-12-23' AS Date), 700, 4, N'4', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'suv1', N'Acura CDX', N'https://i.imgur.com/9Bil27S.jpg', N'Black', CAST(N'2015-10-22' AS Date), 560, 5, N'5', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'suv2', N'Acura MDX', N'https://i.imgur.com/6bgQ9ff.jpg', N'White', CAST(N'2001-12-12' AS Date), 300, 6, N'5', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'suv3', N'Aion LX', N'https://i.imgur.com/JO0Tk1E.jpg', N'Blue', CAST(N'2019-01-31' AS Date), 400, 7, N'5', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'suv4', N'Alfa Romeo Stelvio', N'https://i.imgur.com/VeynouJ.jpg', N'Red', CAST(N'2017-02-22' AS Date), 460, 3, N'5', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [categoryId], [description]) VALUES (N'suv5', N'Aston Martin DBX', N'https://i.imgur.com/akB1P2l.jpg', N'White', CAST(N'2020-01-17' AS Date), 360, 2, N'5', N'4 seats, 2 bags, 5 doors, Air Conditioning, Automatic, Fuel policy
: 
Full to full')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'1', N'Economy')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'2', N'Compact')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'3', N'Intermediate')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'4', N'Luxury')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'5', N'Suv')
INSERT [dbo].[tblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'sale10', CAST(N'2021-01-10' AS Date), 10)
INSERT [dbo].[tblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'sale15', CAST(N'2021-02-10' AS Date), 15)
INSERT [dbo].[tblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'sale5', CAST(N'2021-11-05' AS Date), 5)
INSERT [dbo].[tblOrder] ([orderID], [bookingDate], [status], [userID], [returnDate], [totalAmount], [totalAfterDiscount]) VALUES (N'100221-035604-171442', CAST(N'2021-02-12' AS Date), N'active', N'nhoxroney9x1@gmail.com', CAST(N'2021-02-14' AS Date), 3520, 3520)
INSERT [dbo].[tblOrder] ([orderID], [bookingDate], [status], [userID], [returnDate], [totalAmount], [totalAfterDiscount]) VALUES (N'100221-045632-567160', CAST(N'2021-02-12' AS Date), N'active', N'hoangvd', CAST(N'2021-02-15' AS Date), 3600, 3600)
INSERT [dbo].[tblOrder] ([orderID], [bookingDate], [status], [userID], [returnDate], [totalAmount], [totalAfterDiscount]) VALUES (N'100221-111847-084938', CAST(N'2021-02-10' AS Date), N'active', N'hoangvd', CAST(N'2021-02-12' AS Date), 3380, 3380)
INSERT [dbo].[tblOrder] ([orderID], [bookingDate], [status], [userID], [returnDate], [totalAmount], [totalAfterDiscount]) VALUES (N'110221-033028-423553', CAST(N'2021-03-11' AS Date), N'inactive', N'duchuy2k2501@gmail.com', CAST(N'2021-03-15' AS Date), 5840, 4964)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'eco1', N'100221-035604-171442', 2, 600, N'This car is comfortable', 8)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'eco1', N'110221-033028-423553', 1, 300, N'Best Choose', 9)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'inter1', N'100221-045632-567160', 1, 500, N'Edit lan 2', 7)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'inter1', N'100221-111847-084938', 2, 1000, N'Very good car ', 10)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'lux1', N'100221-035604-171442', 1, 700, N'excellent', 9)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'lux1', N'100221-045632-567160', 1, 700, N'Good Car and nice Price', 8)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'lux3', N'100221-111847-084938', 1, 690, N'Rental Price is cheapter than other shop', 9)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'suv3', N'110221-033028-423553', 2, 800, NULL, NULL)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'suv4', N'100221-035604-171442', 1, 460, N'Normal Car', 5)
INSERT [dbo].[tblOrderDetail] ([carID], [orderID], [quantity], [total], [feedback], [point]) VALUES (N'suv5', N'110221-033028-423553', 1, 360, N'Best Price ', 7)
INSERT [dbo].[tblUser] ([email], [phone], [address], [createDate], [status], [role], [password], [fullname]) VALUES (N'duchuy2k2501@gmail.com', N'090123654987', N'Quan 9', CAST(N'2021-02-11' AS Date), N'active', N'MEMBER', N'123', N'Duc Huy 2')
INSERT [dbo].[tblUser] ([email], [phone], [address], [createDate], [status], [role], [password], [fullname]) VALUES (N'hoangvd', N'090123456', N'Quan 9', CAST(N'2021-01-21' AS Date), N'active', N'MEMBER', N'123', N'Duc Hoang')
INSERT [dbo].[tblUser] ([email], [phone], [address], [createDate], [status], [role], [password], [fullname]) VALUES (N'huyvd', N'090123456', N'Quan Binh Thanh', CAST(N'2021-01-20' AS Date), N'active', N'ADMIN', N'123', N'Duc Huy')
INSERT [dbo].[tblUser] ([email], [phone], [address], [createDate], [status], [role], [password], [fullname]) VALUES (N'nhoxroney9x1@gmail.com', N'090123456', N'Quan 12', CAST(N'2021-01-25' AS Date), N'active', N'MEMBER', N'123', N'Huy VD')
ALTER TABLE [dbo].[tblCar]  WITH CHECK ADD FOREIGN KEY([categoryId])
REFERENCES [dbo].[tblCategory] ([categoryID])
GO
ALTER TABLE [dbo].[tblOrder]  WITH CHECK ADD FOREIGN KEY([userID])
REFERENCES [dbo].[tblUser] ([email])
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([carID])
REFERENCES [dbo].[tblCar] ([carID])
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([orderID])
REFERENCES [dbo].[tblOrder] ([orderID])
GO
USE [master]
GO
ALTER DATABASE [CarRental] SET  READ_WRITE 
GO

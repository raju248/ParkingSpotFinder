USE [ParkingManagementSystem]
GO
/****** Object:  Table [dbo].[Ads]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ads](
	[AddID] [int] IDENTITY(1,1) NOT NULL,
	[Address] [varchar](1000) NOT NULL,
	[Rent] [float] NOT NULL,
	[Guard] [int] NOT NULL,
	[Contact] [varchar](13) NOT NULL,
	[SpotOwnerId] [int] NOT NULL,
	[addedDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[AddID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ParkingRequests]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ParkingRequests](
	[RequestId] [int] IDENTITY(1,1) NOT NULL,
	[Location] [varchar](200) NOT NULL,
	[SenderId] [int] NOT NULL,
	[ReceiverId] [int] NULL,
	[Status] [int] NOT NULL,
	[StartTime] [datetime] NULL,
	[EndTime] [datetime] NULL,
	[TotalAmount] [int] NULL,
	[Rating] [int] NULL,
	[Rent] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[RequestId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ParkingSpot]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ParkingSpot](
	[SpotId] [int] IDENTITY(1,1) NOT NULL,
	[Rent] [float] NOT NULL,
	[Address] [varchar](300) NOT NULL,
	[SpotOwnerId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SpotId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ParkingSpotOwner]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ParkingSpotOwner](
	[SpotOwnerId] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SpotOwnerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[PhoneNo] [varchar](11) NOT NULL,
	[Password] [varchar](100) NOT NULL,
	[Type] [int] NOT NULL,
	[Status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[PhoneNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Vehicle]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Vehicle](
	[VehicleId] [int] IDENTITY(1,1) NOT NULL,
	[VehicleLicenseNo] [varchar](300) NOT NULL,
	[VehicleModel] [varchar](200) NULL,
	[VehicleOwnerId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[VehicleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[VehicleLicenseNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VehicleOwner]    Script Date: 29-Aug-20 2:09:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VehicleOwner](
	[VehicleOwnerId] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[VehicleOwnerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Ads]  WITH CHECK ADD FOREIGN KEY([SpotOwnerId])
REFERENCES [dbo].[ParkingSpotOwner] ([SpotOwnerId])
GO
ALTER TABLE [dbo].[ParkingRequests]  WITH CHECK ADD FOREIGN KEY([ReceiverId])
REFERENCES [dbo].[ParkingSpotOwner] ([SpotOwnerId])
GO
ALTER TABLE [dbo].[ParkingRequests]  WITH CHECK ADD FOREIGN KEY([SenderId])
REFERENCES [dbo].[VehicleOwner] ([VehicleOwnerId])
GO
ALTER TABLE [dbo].[ParkingSpot]  WITH CHECK ADD FOREIGN KEY([SpotOwnerId])
REFERENCES [dbo].[ParkingSpotOwner] ([SpotOwnerId])
GO
ALTER TABLE [dbo].[ParkingSpotOwner]  WITH CHECK ADD FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[Vehicle]  WITH CHECK ADD FOREIGN KEY([VehicleOwnerId])
REFERENCES [dbo].[VehicleOwner] ([VehicleOwnerId])
GO
ALTER TABLE [dbo].[VehicleOwner]  WITH CHECK ADD FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[ParkingSpot]  WITH CHECK ADD  CONSTRAINT [Rent] CHECK  (([Rent]>(0)))
GO
ALTER TABLE [dbo].[ParkingSpot] CHECK CONSTRAINT [Rent]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD CHECK  ((len([Password])>=(6)))
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD CHECK  ((len([PhoneNo])=(11) AND NOT [PhoneNo] like '% %' AND [PhoneNo] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'))
GO

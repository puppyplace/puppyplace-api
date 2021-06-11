ALTER TABLE dbo.product
    ADD details NVARCHAR(4000) CHECK(ISJSON(details) = 1),
        variant NVARCHAR(4000) CHECK(ISJSON(variant) = 1),
        specifications NVARCHAR(4000) CHECK(ISJSON(specifications) = 1);
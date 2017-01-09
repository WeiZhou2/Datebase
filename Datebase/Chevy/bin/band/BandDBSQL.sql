DELIMITER $$
DROP PROCEDURE IF EXISTS AlumniAdd$$
CREATE PROCEDURE AlumniAdd(
	IN FirstName CHAR(15),
	IN LastName  CHAR(15),
	IN Hometown  CHAR(20),
	IN SectionID CHAR(15),
	IN Email CHAR(30)
)
BEGIN
    DECLARE maxGrad INT;
    
    SELECT MAX(GraduateID) INTO maxGrad
    FROM Alumni;
    
    SET maxGrad = (maxGrad + 1);

    INSERT INTO Alumni
    VALUES(maxGrad, FirstName, LastName, Hometown, SectionID, Email);
   
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS FeeAdd$$
CREATE PROCEDURE FeeAdd(
	IN stu INT(9),
	IN a TINYINT(1),
	IN b TINYINT(1),
	IN c TINYINT(1),
	IN d TINYINT(1),
	IN e TINYINT(1),
	IN f DECIMAL(10,2)
)
BEGIN
    DECLARE maxFee INT;
    
    SELECT MAX(FeeID) INTO maxFee
    FROM Fees;
    
    SET maxFee = (maxFee + 1);

    INSERT INTO Fees
    VALUES(maxFee, stu, a, b, c, d, e, f);
   
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS InstRentAdd$$
CREATE PROCEDURE InstRentAdd(
	IN stu INT(9),
	IN a CHAR(15),
	IN b CHAR(15),
	IN c DATE
)
BEGIN
    DECLARE maxInst INT;
    DECLARE InstNum INT;
    
    SELECT MAX(InstRentalID) INTO maxInst
    FROM InstrumentRental;
    
    SET maxInst = (maxInst + 1);

    SELECT UnvInstrumentNum INTO InstNum
    FROM UNKInstrument
    WHERE InstrumentName = a
    AND SerialNum = b;

    INSERT INTO InstrumentRental
    VALUES(maxInst, stu, InstNum, c);
    
    UPDATE UNKInstrument
    SET IsRented = 1
    WHERE  UnvInstrumentNum = InstNum;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS LockRentAdd$$
CREATE PROCEDURE LockRentAdd(
	IN stu INT(9),
	IN a CHAR(15),
	IN c DATE
)
BEGIN
    DECLARE maxLock INT;
    DECLARE LockNum INT;
    
    SELECT MAX(LockRentalID) INTO maxLock
    FROM LockerRental;
    
    SET maxLock = (maxLock + 1);
    
    SELECT UnvLockerNum INTO LockNum
    FROM UNKLocker
    WHERE UnvLockerNum = a;

    INSERT INTO LockerRental
    VALUES(maxLock, stu, LockNum, c);
    
    UPDATE UNKLocker
    SET IsRented = 1
    WHERE  UnvLockerNum = LockNum;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS StudentAdd$$
CREATE PROCEDURE StudentAdd(
	IN a INT(9),
	IN b  CHAR(15),
	IN c CHAR(15),
	IN d  CHAR(12),
	IN e CHAR(30),
	IN g  CHAR(20),
	IN h CHAR(15)
)
BEGIN
    DECLARE rowcount INT;
    
    SELECT COUNT(*) INTO rowcount 
    FROM Student
    WHERE a = StudentID;
    
    IF rowcount = 0
    THEN

    INSERT INTO Student
    VALUES(a, b, c, d, e, g, h);
    
    UPDATE Section
    SET NumberInSection = (NumberInSection + 1)
    WHERE SectionID = h;
    
    ELSE
    
    SELECT BreakMyCode;
    
    END IF;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS InstrumentAdd$$
CREATE PROCEDURE InstrumentAdd(
	IN a  CHAR(15),
	IN b CHAR(20)
)
BEGIN
    DECLARE maxI INT;
    
    SELECT MAX(UnvInstrumentNum) INTO maxI 
    FROM UNKInstrument;
    
    SET maxI = (maxI + 1);

    INSERT INTO UNKInstrument
    VALUES(maxI, a, b, 0);
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS UniformAdd$$
CREATE PROCEDURE UniformAdd(
	IN stu INT(9),
	IN a INT(11),
	IN b INT(11),
	IN c INT(11),
	IN d INT(11)
)
BEGIN
    DECLARE maxUn INT;
    DECLARE rowcount INT;
    DECLARE rowcount1 INT;
    DECLARE rowcount2 INT;
    DECLARE rowcount3 INT;
    
    SELECT MAX(UniformID) INTO maxUn 
    FROM Uniform;
    
    SET maxUn = (maxUn + 1);
    
    SELECT COUNT(*) INTO rowcount
    FROM Uniform
    WHERE a = JacketNum;
    SELECT COUNT(*) INTO rowcount1
    FROM Uniform
    WHERE b = PantsNum;
    SELECT COUNT(*) INTO rowcount2
    FROM Uniform
    WHERE c = HatNum;
    SELECT COUNT(*) INTO rowcount3
    FROM Uniform
    WHERE d = BagNum;

    IF rowcount > 0
    THEN
    SELECT BreakMyCode;
    END IF;
    IF rowcount1 > 0
    THEN
    SELECT BreakMyCode;
    END IF;
    IF rowcount2 > 0
    THEN
    SELECT BreakMyCode;
    END IF;
    IF rowcount3 > 0
    THEN
    SELECT BreakMyCode;
    END IF;

    INSERT INTO Uniform
    VALUES(maxUn, stu, a, b, c, d);
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS DELAlum$$
CREATE PROCEDURE DELAlum(
	IN grad INT(11)
)
BEGIN

    DELETE FROM Alumni WHERE GraduateID = grad;
   
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS DELFee$$
CREATE PROCEDURE DELFee(
	IN fee INT(11)
)
BEGIN

    DELETE FROM Fees WHERE FeeID = fee;
   
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS DELIR$$
CREATE PROCEDURE DELIR(
	IN ir INT(11),
	IN instnum INT(11)
)
BEGIN

    DELETE FROM InstrumentRental WHERE InstRentalID = ir;
    
    UPDATE UNKInstrument
    SET IsRented = 0
    WHERE UnvInstrumentNum = instnum;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS DELLR$$
CREATE PROCEDURE DELLR(
	IN lr INT(11),
	IN locknum INT(11)
)
BEGIN

    DELETE FROM LockerRental WHERE LockRentalID = lr;
    
    UPDATE UNKLocker
    SET IsRented = 0
    WHERE UnvLockerNum = locknum;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS DELStu$$
CREATE PROCEDURE DELStu(
	IN stu INT(11),
	IN flag TINYINT(1)
)
BEGIN

    DECLARE FN CHAR(15);
    DECLARE LaN CHAR(15);
    DECLARE hom CHAR(30);
    DECLARE sec CHAR(15);
    
    SELECT FirstName INTO FN
    FROM Student
    WHERE StudentID = stu;
    
    SELECT LastName INTO LaN
    FROM Student
    WHERE StudentID = stu;
    
    SELECT Hometown INTO hom
    FROM Student
    WHERE StudentID = stu;
    
    SELECT SectionID INTO sec
    FROM Student
    WHERE StudentID = stu;

    DELETE FROM Student WHERE StudentID = stu;
    
    UPDATE Section
    SET NumberInSection = (NumberInSection - 1)
    WHERE SectionID = sec;
    
    IF flag = 1
    THEN

    CALL AlumniAdd(FN, LaN, hom, sec, ""); 
    
    END IF;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS DELInst$$
CREATE PROCEDURE DELInst(
	IN sernum CHAR(20)
)
BEGIN

    DECLARE id INT(11);
    
    SELECT UnvInstrumentNum INTO id
    FROM UNKInstrument
    WHERE SerialNum = sernum;

    DELETE FROM UNKInstrument WHERE UnvInstrumentNum = id;
   
END$$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS DELUni$$
CREATE PROCEDURE DELUni(
	IN uni INT(11)
)
BEGIN

    DELETE FROM Uniform WHERE UniformID = uni;
   
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS NewYear$$
CREATE PROCEDURE NewYear(
)
BEGIN

    DELETE FROM Student;
    DELETE FROM Uniform;
    DELETE FROM Fees;
    DELETE FROM InstrumentRental;
    DELETE FROM LockerRental;
    
   
END$$
DELIMITER ;

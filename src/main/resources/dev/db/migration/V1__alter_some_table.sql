
SELECT COUNT(*)
           INTO @columnCount
FROM information_schema.COLUMNS
WHERE COLUMNS.TABLE_NAME = 'users'
  AND COLUMNS.TABLE_SCHEMA = 'audio_books'
  AND COLUMNS.COLUMN_NAME = 'date_of_birth';

SET @alterStatement = IF(@columnCount = 0,
                         'ALTER TABLE users ADD COLUMN date_of_birth DATE DEFAULT NULL;',
                         '');


PREPARE stmt FROM @alterStatement;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
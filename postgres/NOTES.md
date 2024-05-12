# Postgre using notes

To change any feild to boolean with a function cast, this from table "record" with "IS_DELETE" feild from tinyint to boolean

```sql
ALTER TABLE "record"
ALTER "IS_DELETE" DROP DEFAULT,
ALTER "IS_DELETE" TYPE boolean USING 
  CASE WHEN "IS_DELETE"=0 THEN FALSE 
  ELSE TRUE 
END,
ALTER "IS_DELETE" SET NOT NULL;
```

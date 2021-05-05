package database;

public class SQLBuilder {
    private String SQL;

    public SQLBuilder(String by, String language, String genre){
        if (by.equals("By name")){

        }else if (by.equals("By author")){

        }else if (by.equals("By publisher")){

        }else if (by.equals("By ISBN")){

        }
    }

    public String getSQL() {
        return SQL;
    }



}
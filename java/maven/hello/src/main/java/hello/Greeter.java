package hello;

import com.google.gson.Gson;

public class Greeter {
    public class Obj {
        public String data;

        public Obj() {
            this.data = "Oh my, dependancy is so great";
        }

        public Obj(String data) {
            this.data = data;
        }

        public String toJSON() {
            Gson result = new Gson();
            return result.toJson(this);
        }
    }

    public void sayHello() {
        System.out.println("Hello world!");
        System.out.printf("Json Obj = %s\n", new Obj().toJSON());
        System.out.printf("Json Obj = %s\n", new Obj("That now it no longer my \"resonsibility\"").toJSON());
    }
}

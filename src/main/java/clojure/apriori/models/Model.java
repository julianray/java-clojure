package clojure.apriori.models;

/**
 * Abstract Model class.
 */
public abstract class Model implements java.io.Serializable {

   private String name;

   public String getName() {
      return this.name;
   }
   public void setName(String name) {
      this.name = name;
   }
}
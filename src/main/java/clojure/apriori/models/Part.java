package clojure.apriori.models;

public class Part extends Model {

   private Integer batchSize;
   private Double length;
   private Double width;
   private Double thickness;

   public Integer getBatchSize() {
      return this.batchSize;
   }
   public void setBatchSize(Integer batchSize) {
      this.batchSize = batchSize;
   }

   public Double getLength() {
      return this.length;
   }
   public void setLength(Double length) {
      this.length = length;
   }
   public Double getWidth() {
      return this.width;
   }
   public void setWidth(Double width) {
      this.width = width;
   }
   public Double getThickness() {
      return this.thickness;
   }
   public void setThickness(Double thickness) {
      this.thickness = thickness;
   }
}
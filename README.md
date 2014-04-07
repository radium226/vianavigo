ViaNavigo API
=========
```java
Location departure = ViaNavigo.locate("Châtetlet Les Halles, Paris");
Itinerary itinerary = ViaNavigo.planItinerary(from(departure).to("Jules Joffrin, Paris"));
for (Step step : itinerary.getSteps()) {
  //...
}
```

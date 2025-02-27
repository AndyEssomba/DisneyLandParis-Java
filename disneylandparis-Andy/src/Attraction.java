package src;

public class Attraction {

    int visitCount;
    private String name;
    private int attractionId = 0;

    public Attraction(String name) {
        this.name = name;
        attractionId++;
    }


    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttractionId() {
        return attractionId;
    }


    // Methods

//    public boolean checkAvailability(int count) {       //checks availability of the attraction
//        if (visitCount + count <= visitorLimit) {
//            visitCount += count;
//            return true;
//        }
//        return false;
//    }

}

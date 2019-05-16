package org.javasync.idioms;

import javax.tools.StandardLocation;
import java.util.Optional;

public class Person {
    private Optional<Car> car = Optional.empty();

    public String getCarInsuranceName() {
        return car
                .flatMap(Car::getInsurance)
                .flatMap(Insurance::getName)
                .orElse("Unknown");
        /*
        if (this.car.isPresent()) {
            Optional<Insurance> insurance = this.car.get().getInsurance();
            if (insurance.isPresent()) {
                Optional<String> name = insurance.get().getName();
                if(name.isPresent())
                    return name.get();
            }
        }
        return "Unknown";
        */
        /*
        Car car = this.getCar();
        if (car != null) {
            Insurance insurance = car.getInsurance();
            if (insurance != null) {
                  return insurance.getName();
            }
        }
        return "Unknown";
        */
    }
}
class Car {
    private  Optional<Insurance> insurance = Optional.empty();
    public  Optional<Insurance> getInsurance() { return insurance; }
}
class Insurance {
    private  Optional<String> name = Optional.empty();
    public  Optional<String> getName() { return name; }
}
package com.wisdom.service.impl;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by 赵得良 on 21/09/2016.
 */
public class Test1 {

    public static void main(String[] args) {
        ArrayList<ShopAppointServiceDTO> list = new ArrayList<>();
        ShopAppointServiceDTO dto = new ShopAppointServiceDTO();
        dto.setId("1");
        list.add(dto);
        dto.setId("2");
        list.add(dto);
        list.forEach(e -> e.setId("12"));
        list.forEach(e ->{
            if(e.getId().equals("12")){
                System.out.println("args = [" + "哈哈哈哈哈哈哈" + "]");
            }
        });
        Runnable aNew = Car::new;
        Stream.of("a", "c", null, "d")
                .filter(Objects::nonNull)
                .forEach(System.out::println);

        Optional< String > fullName = Optional.ofNullable( null );
        System.out.println( "Full Name is set? " + fullName.isPresent() );
        System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
        System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );

        String str ="hello!";
        String identify =str.substring(str.length()-1, str.length());
        System.out.println("args = [" + identify + "]");

    }

    public static void aa(String str){
        System.out.println("---------");
    }

    final Car car = Car.create( Car::new );

    public static class Car {
        public static Car create( final Supplier< Car > supplier ) {
            return supplier.get();
        }

        public static void collide( final Car car ) {
            System.out.println( "Collided " + car.toString() );
        }

        public void follow( final Car another ) {
            System.out.println( "Following the " + another.toString() );
        }

        public void repair() {
            System.out.println( "Repaired " + this.toString() );
        }
    }

}

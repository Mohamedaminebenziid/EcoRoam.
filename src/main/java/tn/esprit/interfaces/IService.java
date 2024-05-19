package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{

        public void addDestination( T t);

        public void updateDestination(T t);

        java.util.List<T> getallDestination();
        public boolean isDestinationInReservation(int id );

        void deleteDestination(int id);
        T getoneDestination(int id);



    }


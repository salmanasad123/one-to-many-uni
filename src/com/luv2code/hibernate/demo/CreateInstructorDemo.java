package com.luv2code.hibernate.demo;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructorDemo {

    public static void main(String[] args) {


        // create session factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)  // tell hibernate about our entity classes
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            // create the objects
            Instructor instructor = new Instructor("Susan", "Public", "susan@luv2code.com");

            InstructorDetail instructorDetail = new InstructorDetail("youtube.com/susan","Music");

            // associate the objects (instructor and instructor detail)
            instructor.setInstructorDetail(instructorDetail);

            // start a session
            session.beginTransaction();

            // save the instructor , this will also save instructorDetails because we have CascadeType.ALL
            session.save(instructor);

            session.getTransaction().commit();

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}

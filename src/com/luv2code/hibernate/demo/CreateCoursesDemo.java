package com.luv2code.hibernate.demo;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCoursesDemo {

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
            // start a session
            session.beginTransaction();

            // get the instructor from database using ID
            int theId = 1;
            Instructor instructor = session.get(Instructor.class, theId);

            // create some courses
            Course course1 = new Course("JAVA");
            Course course2 = new Course("AirGuitar - the ultimate guide");

            // add courses to the instructor , associate courses with instructor
            instructor.addCourse(course1);
            instructor.addCourse(course2);

            // save the courses in the database

            session.save(course1);
            session.save(course2);

            // commit the transaction
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

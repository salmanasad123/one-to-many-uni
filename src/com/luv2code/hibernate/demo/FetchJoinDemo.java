package com.luv2code.hibernate.demo;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class FetchJoinDemo {

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

            // Hibernate Query with HQL

            // get the instructor from database using ID
            int theId = 1;

            // when this query is executed it will load the instructor and all its courses all at once
            // from database into the memory
            // :theInstructorId this is the parameter that we set later using query.setParameter
            Query<Instructor> query = session.createQuery("select i from instructor i "
                            + "JOIN FETCH i.courses "
                            + "where i.id:theInstructorId",
                    Instructor.class);

            // set parameter on query
            query.setParameter("theInstructorId", theId);

            // execute the query and save the result into instructor object
            Instructor instructor = query.getSingleResult();

            // get instructor courses using the getter method, at this point everything is loaded the instructor
            // and all of his courses are loaded in memory
            System.out.println("Instructor: " + instructor);

            // close the session
            session.close();

            // since courses are lazy loaded this will fail because courses are load from database into memory
            // only when this getter is called because the fetchType is lazy
            // we close the session before courses are loaded , so this should fail
            List<Course> courseList = instructor.getCourses();

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

package com.luv2code.hibernate.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "title")
    private String courseTitle;

    // a course have manyToOne relationship with instructor, many courses can be taught by one instructor
    // a course will have reference to instructor, column in course table that allow us to find associated instructor
    // @JoinColumn is the column in the course table
    // we did not give CascadeType.REMOVE because we don't want to remove instructor if we delete course
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // a course can have many reviews, we load the reviews lazily on demand
    // and when we delete a course the reivews should be deleted
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_id")
    private List<Review> reviews;

    public Course() {
    }

    // select one field because id is auto-generated and we will insert instructor manually
    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + Id +
                ", courseTitle='" + courseTitle + '\'' +
                ", instructor=" + instructor +
                '}';
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);

    }
}

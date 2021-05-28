package groupon_copycat.Spring_Boot.beans;

import groupon_copycat.Spring_Boot.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "coupons")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;
    @Column
    private int amount;
    @Column
    private double price;
    @Column
    private String title, description, image;
    @Column
    private Date startDate, endDate;
    @Column
    private CategoryType category;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
//    @JsonIgnore
    private Company company;

    public Coupon(int amount, double price, String title, String description, String image, Date startDate,
                  Date endDate, CategoryType category /*, Company company*/) {
        this.amount = amount;
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        //this.company = company;
    }

    public Coupon() { // ctor for Hibernate
    }

    @Override
    public String toString() {
        return "Coupon [couponId=" + couponId + ", amount=" + amount + ", price=" + price + ", title=" + title
                + ", description=" + description + ", image=" + image + ", startDate=" + startDate + ", endDate="
                + endDate + ", category=" + category + ", company=" + company + "]";
    }

    // for .contains()
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coupon) {
            return ((Coupon) obj).getCouponId() == this.getCouponId();
        }
        return false;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCouponId() {
        return couponId;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public Company getCompany() {
        return company;
    }

    /**
     * <b>
     * <h1>DO NOT USE ANYWHERE OUTSIDE COMPANY FACADE</h1></b>
     * otherwise this can (if not used carefully) bypass some checks
     */
    public void setCompany(Company company) {
        this.company = company;
    }

}

package com.gpcglobaltechnologycenter.internship_project.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "Products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
    @XmlElement(name = "Product")
    private List<Product> products;
}

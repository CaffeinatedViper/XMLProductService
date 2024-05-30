package com.gpcglobaltechnologycenter.internship_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @XmlAttribute
    private int id;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Category")
    private String category;
    @XmlElement(name = "PartNumberNR")
    private String partNumberNR;
    @XmlElement(name = "CompanyName")
    private String companyName;
    @XmlElement(name = "Active")
    private boolean active;
}

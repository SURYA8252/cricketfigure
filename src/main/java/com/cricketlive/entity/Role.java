package com.cricketlive.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
	@Id
    private int id;
    private String name;
}

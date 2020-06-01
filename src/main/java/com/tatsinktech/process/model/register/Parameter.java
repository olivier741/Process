/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;

import com.tatsinktech.process.model.AbstractModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString (exclude = "listCommand")
@Table(name = "parameter")
public class Parameter extends AbstractModel<Long> {

    @Column(name ="param_name",nullable = false, unique = true)
    private String paramName;

    @Column(name ="param_length",nullable = true)
    private int paramLength;

    @Column(name ="param_pattern",nullable = true)
    private String paramPattern;


    @OneToMany(mappedBy = "parameter")
    private Set<Command> listCommand = new HashSet<>();

}

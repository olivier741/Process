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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@ToString
@Table(name = "command",uniqueConstraints={@UniqueConstraint(columnNames = {"command_code","action_id"})})
public class Command extends AbstractModel<Long> {
        
    @Column(name= "command_name",nullable = false, unique = true)
    private String commandName;
    
    @Column(name = "command_code",nullable = false, unique = false)
    private String commandCode;
    
    @Column(name = "split_separator",nullable = true)
    private String splitSeparator;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_id", nullable = true)
    private Action action;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parameter_id", nullable = true)
    private Parameter parameter;
       
    @OneToMany(mappedBy = "command")
    private Set<Notification_Conf> listConfig = new HashSet<>();

     
}

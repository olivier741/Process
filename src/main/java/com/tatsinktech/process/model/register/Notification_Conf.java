/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;


import com.tatsinktech.process.model.AbstractModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@ToString
@Table(name = "notification_conf")
public class Notification_Conf extends AbstractModel<Long> {

    @Column(name = "nofication_name", nullable = false, unique = true)
    private String noficationName;

    @Column(name = "nofication_value", nullable = true)
    @Lob
    private String notificationValue;

    @Column(name = "default_value", nullable = true)
    @Lob
    private String defaultValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "command_id", nullable = true)
    private Command command;

}

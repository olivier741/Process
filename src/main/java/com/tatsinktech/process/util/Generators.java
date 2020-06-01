/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.util;

import java.util.Arrays;
import java.util.List;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

/**
 *
 * @author olivier.tatsinkou
 */
public class Generators {
    
    public static String generateRandomDigit(int size) {

		List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.Digit));
		PasswordGenerator generator = new PasswordGenerator();
		String gen_string = generator.generatePassword(size, rules);
		return gen_string;
	}
    
   
}

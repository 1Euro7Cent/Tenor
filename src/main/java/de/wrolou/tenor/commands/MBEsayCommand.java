package de.wrolou.tenor.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.Entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TGG on 9/02/2020.
 *
 * Adds a command "mbesay"
 * Works exactly the same as the vanilla "say" command except that the words are converted to pig latin
 * https://www.geeksforgeeks.org/encoding-word-pig-latin/
 *
 * Information on permission levels for servers:
 * https://nodecraft.com/support/games/minecraft/how-to-set-a-player-as-op-admin
 *
 * https://github.com/Mojang/brigadier for syntax
 *
 */
public class MBEsayCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    LiteralArgumentBuilder<CommandSourceStack> mbesayCommand
            = Commands.literal("mbesay")
                 .requires((commandSource) -> commandSource.hasPermission(2))
                 .then(Commands.argument("message", MessageArgument.message())
                         .executes(MBEsayCommand::sendPigLatinMessage)
                      );

    dispatcher.register(mbesayCommand);
  }

  private static int sendPigLatinMessage(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
    Component messageValue = MessageArgument.getMessage(commandContext, "message");
    String unformattedText = messageValue.getString();
    String pigifiedText = convertParagraphToPigLatin(unformattedText);
   /* ChatComponentText pigifiedTextComponent = new ChatComponentText(pigifiedText);

    TextComponentString finalText = new TranslatableContents("",commandContext.getSource().getDisplayName(),pigifiedText);*/



    Entity entity = commandContext.getSource().getEntity();
    entity.sendSystemMessage(messageValue);
    return 1;
  }

  /**
   * Read the command's "message" argument, convert it to pig latin, then send as a chat message
   */
 /* static int sendPigLatinMessage(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {

  }*/

  /**
   * Break the body of text into words and convert each word into its pig latin equivalent
   */
  static String convertParagraphToPigLatin(String input) {
    String LETTERS_OR_NONLETTERS = "([a-zA-Z]+|[^a-zA-Z]+)"; // match either a group of letters, or a group of non-letters
    String LETTERS_ONLY = "[a-zA-Z]+";

    Pattern lettersOrNonLettersPatten = Pattern.compile(LETTERS_OR_NONLETTERS);
    Matcher matcher = lettersOrNonLettersPatten.matcher(input);

    StringBuilder output = new StringBuilder();
    while(matcher.find()) {
      String tokenFound = matcher.group();
      if (tokenFound.matches(LETTERS_ONLY)) {
        output.append(convertWordToPigLatin(tokenFound));
      } else {
        output.append(tokenFound);
      }
    }
    return output.toString();
  }

  /**
   * Return true if this character is an English vowel
   */
  static boolean isVowel(char c) {
    return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' ||
            c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
  }

  /**
   * Given a word, convert it to Pig Latin (remove first group of consonants, and put at end followed by "ay"), e.g.
   * "pig" = "igpay"
   * "smile" = "ilesmay"
   * if it starts with a vowel, just append "way"
   * "eat" = "eatway"
   */
  static String convertWordToPigLatin(String s) {
    // the index of the first vowel is stored.
    int len = s.length();
    int index = -1;
    for (int i = 0; i < len; i++) {
      if (isVowel(s.charAt(i))) {
        index = i;
        break;
      }
    }

    // Pig Latin is possible only if vowels are present
    if (index == -1) return s;
    if (index == 0) return s + "way";
    // Take all characters after index (including
    // index). Append all characters which are before
    // index. Finally append "ay"
    return s.substring(index) + s.substring(0, index) + "ay";
  }
}

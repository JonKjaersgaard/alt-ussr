package dcd.highlevel.rdcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import dcd.highlevel.ast.Program;
import dcd.highlevel.rdcd.parser.lexer.Lexer;
import dcd.highlevel.rdcd.parser.lexer.LexerException;
import dcd.highlevel.rdcd.parser.node.Start;
import dcd.highlevel.rdcd.parser.parser.Parser;
import dcd.highlevel.rdcd.parser.parser.ParserException;

public class Main extends Compiler {

    /**
     * @param args
     */
    public static void main(String[] args) {
        command_args = args;
        new Main().main();
    }

    private static String[] command_args;
    
    @Override
    public Program getProgram() {
        long start_time, stop_time; // time compilation

        if (command_args.length < 1) {
            throw new Error("Bad usage, must give input file as argument");
        }

        try {
            start_time = System.currentTimeMillis();

            // create lexer
            Lexer lexer = new Lexer (new PushbackReader(new BufferedReader(new FileReader(command_args[0])), 1024));

            // parse program
            Parser parser = new Parser(lexer);

            Start ast = parser.parse();
            
            ASTGenerator generator = new ASTGenerator();
            ast.apply(generator);
            return generator.getAST();
            
        } catch(IOException exn) {
            throw new Error("I/O: "+exn);
        } catch (ParserException exn) {
            throw new Error("Parse error: "+exn);
        } catch (LexerException exn) {
            throw new Error("Lexer error: "+exn);
        }
    }
}

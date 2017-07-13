/* Generated By:JJTree&JavaCC: Do not edit this line. MyTest.java */
package mytest;

import java.io.*;
import registers.Register;

public class MyTest/*@bgen(jjtree)*/implements MyTestTreeConstants, MyTestConstants {/*@bgen(jjtree)*/
  protected static JJTMyTestState jjtree = new JJTMyTestState();
  public static void main(String args []) throws Exception
  {

    MyTest parser = new MyTest(new FileReader("c:/Users/moi/Documents/GitHub/MyProj/Myproj/src/mytest/test.txt"));
        SimpleNode root = parser.prog();

    System.out.println("Abstract Syntax Tree:");
    root.dump(" ");

    System.out.println("Prog:");
    Visitors vi = new Visitors();
    root.jjtAccept(vi,null);

    vi.print();

   }

  static final public SimpleNode prog() throws ParseException {
 /*@bgen(jjtree) prog */
  ASTprog jjtn000 = new ASTprog(JJTPROG);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MOV:
        case ADD:
        case SUB:
        case RSB:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        instr();
      }
      jj_consume_token(0);
                       jjtree.closeNodeScope(jjtn000, true);
                       jjtc000 = false;
                       {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  static final public void instr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MOV:
      mov();
      break;
    case ADD:
      add();
      break;
    case SUB:
      sub();
      break;
    case RSB:
      rsb();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void mov() throws ParseException {
    jj_consume_token(MOV);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case REGISTER:
        ASTdecl jjtn001 = new ASTdecl(JJTDECL);
        boolean jjtc001 = true;
        jjtree.openNodeScope(jjtn001);
      try {
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte001) {
        if (jjtc001) {
          jjtree.clearNodeScope(jjtn001);
          jjtc001 = false;
        } else {
          jjtree.popNode();
        }
        if (jjte001 instanceof RuntimeException) {
          {if (true) throw (RuntimeException)jjte001;}
        }
        if (jjte001 instanceof ParseException) {
          {if (true) throw (ParseException)jjte001;}
        }
        {if (true) throw (Error)jjte001;}
      } finally {
        if (jjtc001) {
          jjtree.closeNodeScope(jjtn001,  2);
        }
      }
      break;
    case COND:
        ASTdeclC jjtn002 = new ASTdeclC(JJTDECLC);
        boolean jjtc002 = true;
        jjtree.openNodeScope(jjtn002);
      try {
        cond();
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[3] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte002) {
        if (jjtc002) {
          jjtree.clearNodeScope(jjtn002);
          jjtc002 = false;
        } else {
          jjtree.popNode();
        }
        if (jjte002 instanceof RuntimeException) {
          {if (true) throw (RuntimeException)jjte002;}
        }
        if (jjte002 instanceof ParseException) {
          {if (true) throw (ParseException)jjte002;}
        }
        {if (true) throw (Error)jjte002;}
      } finally {
        if (jjtc002) {
          jjtree.closeNodeScope(jjtn002,  3);
        }
      }
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void add() throws ParseException {
    jj_consume_token(ADD);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case REGISTER:
      ASTadd jjtn001 = new ASTadd(JJTADD);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[6] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte001;}
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  3);
      }
      }
      break;
    case COND:
      ASTaddC jjtn002 = new ASTaddC(JJTADDC);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
      try {
        cond();
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[8] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte002;}
      }
      if (jjte002 instanceof ParseException) {
        {if (true) throw (ParseException)jjte002;}
      }
      {if (true) throw (Error)jjte002;}
      } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  4);
      }
      }
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void sub() throws ParseException {
    jj_consume_token(SUB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case REGISTER:
      ASTsub jjtn001 = new ASTsub(JJTSUB);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[11] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte001;}
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  3);
      }
      }
      break;
    case COND:
      ASTsubC jjtn002 = new ASTsubC(JJTSUBC);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
      try {
        cond();
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte002;}
      }
      if (jjte002 instanceof ParseException) {
        {if (true) throw (ParseException)jjte002;}
      }
      {if (true) throw (Error)jjte002;}
      } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  4);
      }
      }
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void rsb() throws ParseException {
    jj_consume_token(RSB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case REGISTER:
      ASTrsb jjtn001 = new ASTrsb(JJTRSB);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[15] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[16] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte001;}
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  3);
      }
      }
      break;
    case COND:
      ASTrsbC jjtn002 = new ASTrsbC(JJTRSBC);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
      try {
        cond();
        register();
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[17] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          number();
          break;
        case REGISTER:
          register();
          break;
        default:
          jj_la1[18] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte002;}
      }
      if (jjte002 instanceof ParseException) {
        {if (true) throw (ParseException)jjte002;}
      }
      {if (true) throw (Error)jjte002;}
      } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  4);
      }
      }
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void cond() throws ParseException {
 /*@bgen(jjtree) cond */
 ASTcond jjtn000 = new ASTcond(JJTCOND);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(COND);
                 jjtree.closeNodeScope(jjtn000, true);
                 jjtc000 = false;
                 jjtn000.value = t.image;
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  static final public void register() throws ParseException {
 /*@bgen(jjtree) register */
 ASTregister jjtn000 = new ASTregister(JJTREGISTER);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(REGISTER);
                    jjtree.closeNodeScope(jjtn000, true);
                    jjtc000 = false;
                    jjtn000.data.put("reg", t.image.toString());
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  static final public void number() throws ParseException {
 /*@bgen(jjtree) number */
 ASTnumber jjtn000 = new ASTnumber(JJTNUMBER);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(NUMBER);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                  jjtn000.data.put("value", t.image.toString());
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public MyTestTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[20];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x3c0,0x3c0,0xc00,0xc00,0x820,0xc00,0xc00,0xc00,0xc00,0x820,0xc00,0xc00,0xc00,0xc00,0x820,0xc00,0xc00,0xc00,0xc00,0x820,};
   }

  /** Constructor with InputStream. */
  public MyTest(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MyTest(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MyTestTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MyTest(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MyTestTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MyTest(MyTestTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MyTestTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[13];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 20; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 13; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

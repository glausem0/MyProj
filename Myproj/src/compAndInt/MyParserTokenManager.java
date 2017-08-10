/* Generated By:JJTree&JavaCC: Do not edit this line. MyParserTokenManager.java */
package compAndInt;
import java.io.*;
import java.util.HashMap;
import instructions.*;
import memory.Memory;
import registers.*;

/** Token Manager. */
public class MyParserTokenManager implements MyParserConstants
{

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x40L) != 0L)
            return 59;
         if ((active0 & 0x800000000L) != 0L)
            return 52;
         if ((active0 & 0x60000000L) != 0L)
            return 60;
         if ((active0 & 0x1818000L) != 0L)
            return 2;
         if ((active0 & 0x602000L) != 0L)
            return 24;
         if ((active0 & 0x34006180200L) != 0L)
            return 8;
         if ((active0 & 0xa000000c00L) != 0L)
            return 57;
         return -1;
      case 1:
         if ((active0 & 0xc00L) != 0L)
         {
            jjmatchedKind = 8;
            jjmatchedPos = 1;
            return -1;
         }
         return -1;
      case 2:
         if ((active0 & 0xc00L) != 0L)
         {
            if (jjmatchedPos < 1)
            {
               jjmatchedKind = 8;
               jjmatchedPos = 1;
            }
            return -1;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjStopAtPos(0, 55);
      case 44:
         return jjStopAtPos(0, 48);
      case 45:
         return jjStopAtPos(0, 50);
      case 87:
         return jjMoveStringLiteralDfa1_0(0x40000000000L);
      case 91:
         return jjStopAtPos(0, 49);
      case 93:
         return jjStopAtPos(0, 51);
      case 94:
         return jjStopAtPos(0, 54);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x200061000L);
      case 98:
         jjmatchedKind = 5;
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x60000000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 104:
         return jjStartNfaWithStates_0(0, 6, 59);
      case 108:
         return jjMoveStringLiteralDfa1_0(0xa000000c00L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x1818000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x602000L);
      case 115:
         jjmatchedKind = 9;
         return jjMoveStringLiteralDfa1_0(0x34006180000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x180000000L);
      case 117:
         return jjMoveStringLiteralDfa1_0(0x18000000L);
      case 123:
         return jjStopAtPos(0, 52);
      case 125:
         return jjStopAtPos(0, 53);
      default :
         return jjMoveNfa_0(1, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000000000L);
      case 98:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000L);
      case 100:
         return jjMoveStringLiteralDfa2_0(active0, 0xa000060000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000L);
      case 109:
         return jjMoveStringLiteralDfa2_0(active0, 0x7e000000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000a000L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000000L);
      case 115:
         return jjMoveStringLiteralDfa2_0(active0, 0x100601c00L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x14000000000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x1080000L);
      case 118:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 119:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 73:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000000000L);
      case 97:
         if ((active0 & 0x800000L) != 0L)
            return jjStopAtPos(2, 23);
         break;
      case 98:
         if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(2, 19);
         else if ((active0 & 0x200000L) != 0L)
            return jjStopAtPos(2, 21);
         break;
      case 99:
         if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(2, 18);
         else if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(2, 20);
         else if ((active0 & 0x400000L) != 0L)
            return jjStopAtPos(2, 22);
         else if ((active0 & 0x400000000L) != 0L)
            return jjStopAtPos(2, 34);
         break;
      case 100:
         if ((active0 & 0x20000L) != 0L)
            return jjStopAtPos(2, 17);
         else if ((active0 & 0x200000000L) != 0L)
            return jjStopAtPos(2, 33);
         break;
      case 108:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(2, 10);
         else if ((active0 & 0x1000000L) != 0L)
            return jjStopAtPos(2, 24);
         return jjMoveStringLiteralDfa3_0(active0, 0xa000000L);
      case 109:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStopAtPos(2, 39);
         else if ((active0 & 0x10000000000L) != 0L)
            return jjStopAtPos(2, 40);
         break;
      case 110:
         if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(2, 16);
         else if ((active0 & 0x40000000L) != 0L)
            return jjStopAtPos(2, 30);
         break;
      case 112:
         if ((active0 & 0x20000000L) != 0L)
            return jjStopAtPos(2, 29);
         else if ((active0 & 0x20000000000L) != 0L)
            return jjStopAtPos(2, 41);
         break;
      case 113:
         if ((active0 & 0x80000000L) != 0L)
            return jjStopAtPos(2, 31);
         break;
      case 114:
         if ((active0 & 0x800L) != 0L)
            return jjStopAtPos(2, 11);
         else if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(2, 12);
         else if ((active0 & 0x2000L) != 0L)
            return jjStopAtPos(2, 13);
         else if ((active0 & 0x800000000L) != 0L)
            return jjStopAtPos(2, 35);
         else if ((active0 & 0x1000000000L) != 0L)
            return jjStopAtPos(2, 36);
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStopAtPos(2, 37);
         else if ((active0 & 0x4000000000L) != 0L)
            return jjStopAtPos(2, 38);
         break;
      case 116:
         if ((active0 & 0x100000000L) != 0L)
            return jjStopAtPos(2, 32);
         break;
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x14000000L);
      case 118:
         if ((active0 & 0x8000L) != 0L)
            return jjStopAtPos(2, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 84:
         if ((active0 & 0x40000000000L) != 0L)
            return jjStopAtPos(3, 42);
         break;
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0xa000000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x14000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 108:
         if ((active0 & 0x2000000L) != 0L)
            return jjStopAtPos(4, 25);
         else if ((active0 & 0x4000000L) != 0L)
            return jjStopAtPos(4, 26);
         else if ((active0 & 0x8000000L) != 0L)
            return jjStopAtPos(4, 27);
         else if ((active0 & 0x10000000L) != 0L)
            return jjStopAtPos(4, 28);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 59;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 46)
                        kind = 46;
                     jjCheckNAdd(14);
                  }
                  else if (curChar == 35)
                     jjCheckNAddTwoStates(35, 37);
                  else if (curChar == 46)
                     jjCheckNAdd(16);
                  else if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 4;
                  if (curChar == 35)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 24:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 23;
                  else if (curChar == 57)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 56)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 55)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 54)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 53)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 52)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 51)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 50)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 22;
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 21;
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 20;
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 19;
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 18;
                  if (curChar == 49)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  break;
               case 4:
                  if (curChar == 60 && kind > 14)
                     kind = 14;
                  break;
               case 5:
                  if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 62 && kind > 14)
                     kind = 14;
                  break;
               case 7:
                  if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 11:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 45)
                     kind = 45;
                  jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 12:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 13:
                  if (curChar == 35)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  jjCheckNAdd(14);
                  break;
               case 15:
                  if (curChar == 46)
                     jjCheckNAdd(16);
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 47)
                     kind = 47;
                  jjCheckNAdd(16);
                  break;
               case 18:
                  if (curChar == 48 && kind > 44)
                     kind = 44;
                  break;
               case 19:
                  if (curChar == 49 && kind > 44)
                     kind = 44;
                  break;
               case 20:
                  if (curChar == 50 && kind > 44)
                     kind = 44;
                  break;
               case 21:
                  if (curChar == 51 && kind > 44)
                     kind = 44;
                  break;
               case 22:
                  if (curChar == 52 && kind > 44)
                     kind = 44;
                  break;
               case 23:
                  if (curChar == 53 && kind > 44)
                     kind = 44;
                  break;
               case 25:
                  if (curChar == 55 && kind > 44)
                     kind = 44;
                  break;
               case 26:
                  if (curChar == 56 && kind > 44)
                     kind = 44;
                  break;
               case 27:
                  if (curChar == 57 && kind > 44)
                     kind = 44;
                  break;
               case 28:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 29:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 30:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 31:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 32:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 33:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 34:
                  if (curChar == 35)
                     jjCheckNAddTwoStates(35, 37);
                  break;
               case 35:
                  if (curChar == 45)
                     jjCheckNAdd(36);
                  break;
               case 36:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 43)
                     kind = 43;
                  jjCheckNAdd(36);
                  break;
               case 37:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 43)
                     kind = 43;
                  jjCheckNAdd(37);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 52:
                  if (curChar == 113)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 97)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  else if (curChar == 100)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  break;
               case 57:
                  if (curChar == 114)
                  {
                     if (kind > 44)
                        kind = 44;
                  }
                  else if (curChar == 101)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 116)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 115)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 111)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  break;
               case 1:
                  if (curChar == 108)
                     jjCheckNAddStates(0, 4);
                  else if (curChar == 112)
                     jjAddStates(5, 6);
                  else if (curChar == 101)
                     jjCheckNAddStates(7, 9);
                  else if (curChar == 100)
                     jjCheckNAddTwoStates(46, 47);
                  else if (curChar == 102)
                     jjCheckNAddTwoStates(49, 46);
                  else if (curChar == 105)
                     jjCheckNAddTwoStates(46, 47);
                  else if (curChar == 103)
                     jjCheckNAddTwoStates(0, 44);
                  else if (curChar == 118)
                     jjCheckNAddTwoStates(39, 40);
                  else if (curChar == 104)
                     jjCheckNAddTwoStates(39, 2);
                  else if (curChar == 99)
                     jjCheckNAddTwoStates(39, 40);
                  else if (curChar == 114)
                     jjAddStates(10, 25);
                  else if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 8;
                  else if (curChar == 109)
                     jjCheckNAdd(2);
                  else if (curChar == 110)
                     jjCheckNAdd(0);
                  break;
               case 60:
                  if (curChar == 99)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 115)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  break;
               case 59:
                  if (curChar == 105)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 115)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  break;
               case 0:
                  if (curChar == 101 && kind > 8)
                     kind = 8;
                  break;
               case 2:
                  if (curChar == 105 && kind > 8)
                     kind = 8;
                  break;
               case 3:
                  if (curChar == 109)
                     jjCheckNAdd(2);
                  break;
               case 8:
                  if (curChar == 112 && kind > 44)
                     kind = 44;
                  break;
               case 9:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 120)
                     jjCheckNAdd(11);
                  break;
               case 11:
                  if ((0x7e00000000L & l) == 0L)
                     break;
                  if (kind > 45)
                     kind = 45;
                  jjCheckNAdd(11);
                  break;
               case 16:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 47)
                     kind = 47;
                  jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 17:
                  if (curChar == 114)
                     jjAddStates(10, 25);
                  break;
               case 38:
                  if (curChar == 99)
                     jjCheckNAddTwoStates(39, 40);
                  break;
               case 39:
                  if (curChar == 115 && kind > 8)
                     kind = 8;
                  break;
               case 40:
                  if (curChar == 99 && kind > 8)
                     kind = 8;
                  break;
               case 41:
                  if (curChar == 104)
                     jjCheckNAddTwoStates(39, 2);
                  break;
               case 42:
                  if (curChar == 118)
                     jjCheckNAddTwoStates(39, 40);
                  break;
               case 43:
                  if (curChar == 103)
                     jjCheckNAddTwoStates(0, 44);
                  break;
               case 44:
                  if (curChar == 116 && kind > 8)
                     kind = 8;
                  break;
               case 45:
                  if (curChar == 105)
                     jjCheckNAddTwoStates(46, 47);
                  break;
               case 46:
                  if (curChar == 97 && kind > 7)
                     kind = 7;
                  break;
               case 47:
                  if (curChar == 98 && kind > 7)
                     kind = 7;
                  break;
               case 48:
                  if (curChar == 102)
                     jjCheckNAddTwoStates(49, 46);
                  break;
               case 49:
                  if (curChar == 100 && kind > 7)
                     kind = 7;
                  break;
               case 50:
                  if (curChar == 100)
                     jjCheckNAddTwoStates(46, 47);
                  break;
               case 51:
                  if (curChar == 101)
                     jjCheckNAddStates(7, 9);
                  break;
               case 53:
                  if (curChar == 112)
                     jjAddStates(5, 6);
                  break;
               case 54:
                  if (curChar == 108 && kind > 8)
                     kind = 8;
                  break;
               case 55:
                  if (curChar == 99 && kind > 44)
                     kind = 44;
                  break;
               case 56:
                  if (curChar == 108)
                     jjCheckNAddStates(0, 4);
                  break;
               case 58:
                  if (curChar == 114 && kind > 44)
                     kind = 44;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 59 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   57, 39, 44, 0, 58, 54, 55, 49, 46, 52, 18, 19, 20, 21, 22, 23, 
   24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\142", "\150", null, null, "\163", 
"\154\163\154", "\154\163\162", "\141\163\162", "\162\157\162", null, "\155\157\166", 
"\155\166\156", "\141\144\144", "\141\144\143", "\163\165\142", "\163\142\143", 
"\162\163\142", "\162\163\143", "\155\154\141", "\155\165\154", "\163\155\154\141\154", 
"\163\155\165\154\154", "\165\155\154\141\154", "\165\155\165\154\154", "\143\155\160", 
"\143\155\156", "\164\145\161", "\164\163\164", "\141\156\144", "\142\151\143", 
"\145\157\162", "\157\162\162", "\154\144\162", "\163\164\162", "\154\144\155", 
"\163\164\155", "\163\167\160", "\127\101\111\124", null, null, null, null, null, "\54", 
"\133", "\55", "\135", "\173", "\175", "\136", "\41", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xffffffffffffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[59];
static private final int[] jjstateSet = new int[118];
static protected char curChar;
/** Constructor. */
public MyParserTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public MyParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 59; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}

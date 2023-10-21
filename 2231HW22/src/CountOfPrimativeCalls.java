/**
    * Private constructor so this utility class cannot be instantiated.
    */
   private CountPrimitiveCalls() {
   }


   /**
    * Reports the number of calls to primitive instructions (move, turnleft,
    * turnright, infect, skip) in a given {@code Statement}.
    *
    * @param s
    *            the {@code Statement}
    * @return the number of calls to primitive instructions in {@code s}
    * @ensures
    *
    *          <pre>
    * countOfPrimitiveCalls =
    *  [number of calls to primitive instructions in s]
    *          </pre>
    */
   public static int countOfPrimitiveCalls(Statement s) {
       int count = 0;
       switch (s.kind()) {
           case BLOCK: {
               /*
                * Add up the number of calls to primitive instructions in each
                * nested statement in the BLOCK.
                */

               // TODO - fill in case
               int length = s.lengthOfBlock();
               for (int i = 0; i < length; i++) {
                   Statement subLable = s.removeFromBlock(i);
                   count += countOfPrimitiveCalls(subLable);
                   s.addToBlock(i, subLable);
               }
               break;
           }
           case IF: {
               /*
                * Find the number of calls to primitive instructions in the
                * body of the IF.
                */

               // TODO - fill in case
               Statement subLable = s.newInstance();

               Statement.Condition c = s.disassembleIf(subLable);
               count = countOfPrimitiveCalls(subLable);
               s.assembleIf(c, subLable);

               break;
           }
           case IF_ELSE: {
               /*
                * Add up the number of calls to primitive instructions in the
                * "then" and "else" bodies of the IF_ELSE.
                */

               // TODO - fill in case
               Statement subLabelIf = s.newInstance();
               Statement subLabelElse = s.newInstance();

               Statement.Condition c = s.disassembleIfElse(subLabelIf,
                       subLabelElse);
               count = countOfPrimitiveCalls(subLabelIf)
                       + countOfPrimitiveCalls(subLabelElse);
               s.assembleIfElse(c, subLabelIf, subLabelElse);
               break;
           }
           case WHILE: {
               /*
                * Find the number of calls to primitive instructions in the
                * body of the WHILE.
                */

               // TODO - fill in case
               Statement subLabel = s.newInstance();
               Statement.Condition c = s.disassembleWhile(subLabel);

               count = countOfPrimitiveCalls(subLabel);
               s.assembleWhile(c, subLabel);

               break;
           }
           case CALL: {
               /*
                * This is a leaf: the count can only be 1 or 0. Determine
                * whether this is a call to a primitive instruction or not.
                */

               // TODO - fill in case
               String label = s.disassembleCall();
               if (label.equals("turnright") || label.equals("move")
                       || label.equals("infect") || label.equals("turnleft")
                       || label.equals("skip")) {
                   count++;
               }
               s.assembleCall(label);
               break;
           }
           default: {
               // this will never happen...can you explain why?
               break;
           }
       }
       return count;
   }

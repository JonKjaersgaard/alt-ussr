/* This file was generated by SableCC (http://www.sablecc.org/). */

package dcd.highlevel.rdcd.parser.node;

import dcd.highlevel.rdcd.parser.analysis.*;

@SuppressWarnings("nls")
public final class TPlusPlus extends Token
{
    public TPlusPlus()
    {
        super.setText("++");
    }

    public TPlusPlus(int line, int pos)
    {
        super.setText("++");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPlusPlus(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPlusPlus(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TPlusPlus text.");
    }
}
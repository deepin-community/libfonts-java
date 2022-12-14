/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2006 - 2009 Pentaho Corporation and Contributors.  All rights reserved.
 */

package org.pentaho.reporting.libraries.fonts.monospace;

import org.pentaho.reporting.libraries.fonts.registry.FontFamily;
import org.pentaho.reporting.libraries.fonts.registry.FontRecord;

/**
 * Creation-Date: 13.05.2007, 13:14:25
 *
 * @author Thomas Morgner
 */
public class MonospaceFontFamily implements FontFamily
{
  private String familyName;
  private FontRecord[] fonts;

  public MonospaceFontFamily(final String familyName)
  {
    if (familyName == null)
    {
      throw new NullPointerException();
    }
    this.familyName = familyName;
    this.fonts = new FontRecord[4];
  }

  /**
   * Returns the name of the font family (in english).
   *
   * @return
   */
  public String getFamilyName()
  {
    return familyName;
  }

  public String[] getAllNames()
  {
    return new String[]{ familyName };
  }

  /**
   * This selects the most suitable font in that family. Italics fonts are preferred over oblique fonts.
   *
   * @param bold
   * @param italics
   * @return
   */
  public FontRecord getFontRecord(final boolean bold, final boolean italics)
  {
    int index = 0;
    if (bold)
    {
      index += 1;
    }
    if (italics)
    {
      index += 2;
    }
    if (fonts[index] != null)
    {
      return fonts[index];
    }
    fonts[index] = new MonospaceFontRecord(this, bold, italics);
    return fonts[index];
  }

  public boolean equals(final Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    final MonospaceFontFamily that = (MonospaceFontFamily) o;

    if (!familyName.equals(that.familyName))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    return familyName.hashCode();
  }
}

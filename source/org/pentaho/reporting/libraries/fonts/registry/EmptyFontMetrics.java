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

package org.pentaho.reporting.libraries.fonts.registry;

import org.pentaho.reporting.libraries.fonts.LibFontsDefaults;
import org.pentaho.reporting.libraries.fonts.tools.StrictGeomUtility;

/**
 * An placeholder metrics for buggy fonts.
 *
 * @author Thomas Morgner
 */
public class EmptyFontMetrics implements FontMetrics
{
  private long baseSize;
  private long baseWidth;

  public EmptyFontMetrics(final double baseWidth, final double baseHeight)
  {
    this.baseSize = StrictGeomUtility.toInternalValue(baseHeight);
    this.baseWidth = StrictGeomUtility.toInternalValue(baseWidth);
  }


  public EmptyFontMetrics(final long baseWidth, final long baseHeight)
  {
    this.baseWidth = baseWidth;
    this.baseSize = baseHeight;
  }

  /**
   * Is it guaranteed that the font always returns the same baseline info objct?
   *
   * @return true, if the baseline info in question is always the same, false otherwise.
   */
  public boolean isUniformFontMetrics()
  {
    return true;
  }

  /**
   * From the baseline to the
   *
   * @return
   */
  public long getAscent()
  {
    return StrictGeomUtility.toInternalValue(baseSize * LibFontsDefaults.DEFAULT_ASCENT_SIZE);
  }

  public long getDescent()
  {
    return StrictGeomUtility.toInternalValue(baseSize * LibFontsDefaults.DEFAULT_DESCENT_SIZE);
  }

  public long getLeading()
  {
    return 0;
  }

  /**
   * The height of the lowercase 'x'. This is used as hint, which size the
   * lowercase characters will have.
   *
   * @return
   */
  public long getXHeight()
  {
    return StrictGeomUtility.toInternalValue(baseSize * LibFontsDefaults.DEFAULT_XHEIGHT_SIZE);
  }

  public long getOverlinePosition()
  {
    return getLeading() - Math.max (1000, baseSize / 20);
  }

  public long getUnderlinePosition()
  {
    return getAscent() + Math.max (1000, baseSize / 20);
  }

  public long getStrikeThroughPosition()
  {
    return StrictGeomUtility.toInternalValue(getXHeight() * LibFontsDefaults.DEFAULT_STRIKETHROUGH_POSITION);
  }

  public long getMaxAscent()
  {
    return getAscent();
  }

  public long getMaxDescent()
  {
    return getDescent();
  }

  public long getMaxHeight()
  {
    return baseSize;
  }

  public long getMaxCharAdvance()
  {
    return baseWidth;
  }

  public long getCharWidth(final int codePoint)
  {
    return baseWidth;
  }

  public long getKerning(final int previous, final int codePoint)
  {
    return 0;
  }

  /**
   * Baselines are defined for scripts, not glyphs. A glyph carries script
   * information most of the time (unless it is a neutral characters or just
   * weird).
   *
   * @param c
   * @return
   */
  public BaselineInfo getBaselines(final int c, BaselineInfo info)
  {
    if (info == null)
    {
      info = new BaselineInfo();
    }

    // this is the most dilletantic baseline computation on this planet.
    // But without any font metrics, it is also the best baseline computation :)

    // The ascent is local - but we need the global baseline, relative to the
    // MaxAscent.
    final long maxAscent = getMaxAscent();
    info.setBaseline(BaselineInfo.MATHEMATICAL, maxAscent - getXHeight());
    info.setBaseline(BaselineInfo.IDEOGRAPHIC, getMaxHeight());
    info.setBaseline(BaselineInfo.MIDDLE, maxAscent / 2);
    info.setBaseline(BaselineInfo.ALPHABETIC, maxAscent);
    info.setBaseline(BaselineInfo.CENTRAL, maxAscent / 2);
    info.setBaseline(BaselineInfo.HANGING, maxAscent - getXHeight());
    info.setDominantBaseline(BaselineInfo.ALPHABETIC);
    return info;
  }

  public long getItalicAngle()
  {
    return 0;
  }
}

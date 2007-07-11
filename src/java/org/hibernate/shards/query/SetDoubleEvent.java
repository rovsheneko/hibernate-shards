/**
 * Copyright (C) 2007 Google Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package org.hibernate.shards.query;

import org.hibernate.shards.session.ShardedSessionException;

import org.hibernate.Query;

/**
 * @author maulik@google.com (Maulik Shah)
 */
public class SetDoubleEvent implements QueryEvent {

   private static enum CtorType {
    POSITION_VAL,
    NAME_VAL
  }

  private final CtorType ctorType;
  private final int position;
  private final double val;
  private final String name;

  private SetDoubleEvent(CtorType ctorType, int position, double val, String name) {
    this.ctorType = ctorType;
    this.position = position;
    this.val = val;
    this.name = name;
  }

  public SetDoubleEvent(int position, double val) {
    this(CtorType.POSITION_VAL, position, val, null);
  }

  public SetDoubleEvent(String name, double val) {
    this(CtorType.NAME_VAL, -1, val, name);
  }

  public void onEvent(Query query) {
    switch(ctorType) {
      case POSITION_VAL:
        query.setDouble(position, val);
        break;
      case NAME_VAL:
        query.setDouble(name, val);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown ctor type in SetDoubleEvent: " + ctorType);
    }
  }

}
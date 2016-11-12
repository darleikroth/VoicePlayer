package player;

/*
	This file is part of SwingRecorder version 1.0
	Coded / Copyright 2005 by Bert Szoghy

	webmaster@quadmore.com
	This program is free software; you can redistribute it and/or modify it under the terms
	of the GNU General Public License version 2 as published by the Free Software Foundation;
	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
	the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
	See the GNU General Public License for more details. You should have received a copy of
	the GNU General Public License along with this program; if not, write to the
	Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

	This class stores global variables that will be used to communicate between two
	asynchroneous threads of the SwingRecorder application.
*/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GlobalStorage
{
		private static boolean blnStopIsNext = false;

		public static boolean isStopIsNext()
		{
			//I confused even myself, should've thought of a better name.
			//The logic is:
			//if isPlayNext is true, that means we are paused.
			//if isPlayNext is false, that means we are playing and pause is next.
			return blnStopIsNext;
		}

		public static void setStopIsNext(boolean blnValue)
		{
			blnStopIsNext = blnValue;
		}
}
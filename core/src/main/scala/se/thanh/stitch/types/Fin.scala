/*
 * Copyright 2022 Thanh Le
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.thanh.stitch.types

import scala.compiletime.ops.int.*

enum Fin[Nat <: Int]:
  case FZ[N <: Int]()              extends Fin[S[N]]
  case FS[N <: Int](val n: Fin[N]) extends Fin[S[N]]

object Fin:
  def toInt[Nat <: Int](n: Fin[Nat]): Int =
    n match
      case FZ()   => 0
      case FS(n1) => toInt(n1) + 1

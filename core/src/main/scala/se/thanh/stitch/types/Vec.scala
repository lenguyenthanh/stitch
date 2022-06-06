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

/** Resouces:
  *   - https://docs.scala-lang.org/scala3/reference/metaprogramming/compiletime-ops.html#
  *   - https://blog.oyanglul.us/scala/dotty/en/first-class-types
  */

import scala.compiletime.ops.int.*

enum Vec[Nat, +A]:
  case VNil                                            extends Vec[0, Nothing]
  case VCons[N <: Int, AA](head: AA, tail: Vec[N, AA]) extends Vec[S[N], AA]

object Vec:
  import Vec.*
  import Fin.*
  def combine[N <: Int, M <: Int, A](a: Vec[N, A], b: Vec[M, A]): Vec[N + M, A] =
    (a, b) match
      case (VNil, b)                                => b.asInstanceOf[Vec[N + M, A]]
      case (VCons(head: A, tail: Vec[N - 1, A]), b) => VCons(head, combine(tail, b)).asInstanceOf[Vec[N + M, A]]

  extension [N <: Int, A](vec: Vec[N, A])
    def ++[M <: Int](other: Vec[M, A]) = combine(vec, other)

    def apply(fin: Fin[N]): A =
      (vec, fin) match
        case (VCons(x, _), FZ())   => x
        case (VCons(_, xs), FS(n)) => xs(n)

    def index(a: A): Option[Fin[N]] =
      vec match
        case VNil => None
        case VCons(x, tail) =>
          if x == a then Some(FZ[N - 1]().asInstanceOf[Fin[N]])
          else tail.index(a).map(f => FS(f))

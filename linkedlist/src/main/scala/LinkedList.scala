package cs162.tutorials.linkedlist

trait LinkedList[T] {
  def length: Int

  def hasMore: Boolean
  
  def maybeHead: Option[T]
  
  def map[U](fn: T => U): LinkedList[U]
}

case class ListNode[T](v: T, next: LinkedList[T]) extends LinkedList[T] {
  override def length = 1 + next.length

  override def hasMore: Boolean = ???

  override def maybeHead: Option[T] = ???
  
  override def map[U](fn: T => U): LinkedList[U] = ???
}

case class ListEnd[T]() extends LinkedList[T] {
  override def length = 0

  override def hasMore = ???

  override def maybeHead: Option[T] = ???
  
  override def map[U](fn: T => U): LinkedList[U] = ???
}


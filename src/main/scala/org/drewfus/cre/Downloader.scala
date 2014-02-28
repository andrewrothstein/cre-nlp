package org.drewfus.cre

import akka.actor.Actor

case class DL(link :String)

class Downloader extends Actor {
  
  def receive = {
    case DL(link) => {
      
    }
  }
}
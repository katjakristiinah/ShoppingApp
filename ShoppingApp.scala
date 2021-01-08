package shoppingApp

import scala.collection.mutable.Buffer
import scala.swing._
import scala.swing.event._

object ShoppingApp extends SimpleSwingApplication {

  // All items in shopping list currently
  var allItems = Buffer[Item]()

  // Contents
  val addNewItem = new Label("Add new item: ")
  val itemNameHere = new TextField("item name here", 15)
  val howMany = new Label("How many?: ")
  val addQuantityHere = new TextField("add quantity here", 15)
  val addButton = new Button("Add")

  val newListButton = new Button("Create an empty shopping list")

  val itemDeleted = new TextField("item you want to delete", 15)
  val deleteButton = new Button("Delete")

  var shoppingList = new TextArea(" Your Shopping List: \n \n ", 30, 50)
  shoppingList.editable = false

  // Update the view
  private def update() = {
    shoppingList.text = " Your Shopping List: \n \n "
    allItems.foreach(item => shoppingList.append(item.name.toUpperCase + ", " + item.quantity + " pcs \n "))
  }

  // Layout
  val upperPanel = new FlowPanel
  upperPanel.contents += addNewItem
  upperPanel.contents += itemNameHere
  upperPanel.contents += howMany
  upperPanel.contents += addQuantityHere
  upperPanel.contents += addButton

  val lowerPanel = new FlowPanel
  lowerPanel.contents += newListButton
  lowerPanel.contents += itemDeleted
  lowerPanel.contents += deleteButton

  val mainPanel = new BoxPanel(Orientation.Vertical)
  mainPanel.contents += upperPanel
  mainPanel.contents += shoppingList
  mainPanel.contents += lowerPanel

  val window = new MainFrame
  window.title = "ShoppingApp"
  window.contents = mainPanel

  def top = this.window

  // Events
  listenTo(addButton)
  listenTo(newListButton)
  listenTo(deleteButton)
  reactions += {
    case thisButton: ButtonClicked =>
      if (thisButton.source == addButton) {
        allItems += new Item(itemNameHere.text, addQuantityHere.text.toInt)
        update()
      } else if (thisButton.source == newListButton) {
        allItems.clear()
        update()
      } else if (thisButton.source == deleteButton) {
        val index = allItems.indexWhere(_.name == itemDeleted.text)
        allItems.remove(index)
        update()
      }
  }


}


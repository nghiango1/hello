def normalImport():
    """A module import in a method only accessable in the current method stack call, thus not affect the outer scope"""
    # This import allow us to access datastructure module and myQueue module. These three are the same
    # import datastructure
    # import datastructure as ds
    import datastructure.__init__
     
    try:
        q1 = datastructure.myQueue.QNode(4)  # noqa: F821
        print("This work normally,\nQNode value = ", q1)
    except Exception as e:
        print("Nope, as `datastucture` isn't import/defined yet, we can't do that. Got error: ", e)

    # q = datastructure.PrivateQueue()
    # This will show error with pyright lsp, but mypy will be fine. Regardless this is a perfectly fine python code and run-able without any run-time error
    q2 = datastructure.__myPrivateQueue.QNode(4)
    print("QNode (private) value: ", q2)

def weirdImport():
    import datastructure.__init__ as dsi

    try:
        q1 = datastructure.myQueue.QNode(4)  # noqa: F821
        print("No way,\nQNode value = ", q1)
    except Exception as e:
        print("Nope, as `datastucture` isn't import/defined yet, we can't do that. Got error: ", e)

    try:
        q1 = dsi.myQueue.QNode(4) # This not even catch by pyright LSP
        print("No way,\nQNode value = ", q1)
    except Exception as e:
        print("Nope, as `dsi` isn't expose `myQueue` module, we can't do that. Got error: ", e)

    queue = dsi.Queue()
    queue.add(1)
    queue.add(2)
    queue.add(3)
    print("We can only use exposed class/method/module imported directly (eg: `Queue` (class)) through `dsi`,\nQueue value = ", queue)

def main():
    print("Start normal run\n================")
    normalImport()
    print("\nStart weird run?\n================")
    weirdImport()

if __name__ == "__main__":
    main()

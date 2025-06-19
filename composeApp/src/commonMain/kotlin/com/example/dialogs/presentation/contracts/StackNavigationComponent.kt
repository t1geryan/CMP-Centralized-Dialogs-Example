package com.example.dialogs.presentation.contracts

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.value.Value

interface StackNavigationComponent<out T : NavigationChild> {
    val childStack: Value<ChildStack<*, T>>
}

interface NavigationChild {
    val component: Any
}

fun StackNavigationComponent<*>.findAllStackNavigationSubcomponents(): List<StackNavigationComponent<*>> =
    buildList {
        add(this@findAllStackNavigationSubcomponents)
        addAll(
            this@findAllStackNavigationSubcomponents.childStack.items
                .mapNotNull { child ->
                    (child.instance.component as? StackNavigationComponent<*>)
                        ?.findAllStackNavigationSubcomponents()
                        ?.let { result ->
                            buildList<StackNavigationComponent<*>> {
                                add(child.instance.component as StackNavigationComponent<*>)
                                addAll(result)
                            }
                        }
                }
                .flatten(),
        )
    }
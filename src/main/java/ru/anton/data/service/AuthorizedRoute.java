package ru.anton.data.service;

import com.vaadin.flow.component.Component;

import java.util.Objects;

public final class AuthorizedRoute {
    private String route;
    private String name;
    private Class<? extends Component> view;

    public AuthorizedRoute(String route, String name, Class<? extends Component> view) {
        this.route = route;
        this.name = name;
        this.view = view;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Component> getView() {
        return view;
    }

    public void setView(Class<? extends Component> view) {
        this.view = view;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizedRoute that = (AuthorizedRoute) o;
        return Objects.equals(route, that.route) &&
                Objects.equals(name, that.name) &&
                Objects.equals(view, that.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, name, view);
    }

    @Override
    public String toString() {
        return "AuthorizedRoute{" +
                "route='" + route + '\'' +
                ", name='" + name + '\'' +
                ", view=" + view +
                '}';
    }
}

/*
 * Copyright 2012 Steve Chaloner
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
package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class SecurityRole extends Model implements InheritableRole
{
    @Id
    public Long id;

    public String roleName;

    public static final Finder<Long, SecurityRole> find = new Finder<Long, SecurityRole>(Long.class,
                                                                                         SecurityRole.class);

    @ManyToMany
    @JoinTable(name = "RoleRelationShip",
               joinColumns = @JoinColumn(name = "role_id"),
               inverseJoinColumns = @JoinColumn(name = "inherited_role_id"))
    @OrderColumn(name = "order_index")
    public List<SecurityRole> inheritedRoles;

    public List<? extends InheritableRole> getInheritedRoles()
    {
        return inheritedRoles;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public static SecurityRole findByRoleName(String roleName)
    {
        return find.where()
                   .eq("roleName",
                       roleName)
                   .findUnique();
    }
}

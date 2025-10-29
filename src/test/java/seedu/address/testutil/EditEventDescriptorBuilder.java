package seedu.address.testutil;

import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private final EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details.
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getName());
        descriptor.setDate(event.getDate());
        descriptor.setVenue(event.getVenue());
    }

    /**
     * Sets the {@code EventName} of the descriptor being built.
     * @param name Event name as a string.
     * @return This builder instance for chaining.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setEventName(new EventName(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the descriptor being built.
     * @param date Date string in valid format.
     * @return This builder instance for chaining.
     */
    public EditEventDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Venue} of the descriptor being built.
     * @param venue Venue name as a string.
     * @return This builder instance for chaining.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}

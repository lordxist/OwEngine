java_import 'owengine.model.story.BasicStoryEvent'

class ConcreteCommand < BasicStoryEvent::Command
  def set_call_objects(sym, args)
    @sym = sym
    @args = args
  end

  def init
    get_action_user.send(@sym, *@args)
  end
end

class Shortcut
  attr_reader :link

  def initialize(link)
    @link = link
  end
  
  def method_missing(sym, *args, &block)
    cmd = ConcreteCommand.new($world, @link)
    cmd.set_call_objects(sym, args)
    $world.add_cmd(cmd)
  end
end

def script(&block)
  cmd_list_klass = Class.new(BasicStoryEvent::CmdList)
  cmd_list_klass.send(:define_method, :init, &block)
  $world.set_cmd_list(cmd_list_klass.new($world))
end
